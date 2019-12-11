package com.oisou.applesignin.service

import com.google.gson.Gson
import com.oisou.applesignin.config.AppleAuthServerConfig
import com.oisou.applesignin.model.AppleAuthKeysList
import com.oisou.applesignin.model.AppleAuthPublicKey
import com.oisou.applesignin.model.AppleAuthCredentials
import com.oisou.applesignin.model.AppleVerifyCredentialsResponse
import io.jsonwebtoken.Jwts
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPublicKeySpec
import java.util.Base64

@Service
class AppleAuthService(
    private val appleAuthServerConfig: AppleAuthServerConfig
) {
    fun getApplePublicKeyInfo(): AppleAuthKeysList {
        val gson = Gson()
        val httpGet = HttpGet(appleAuthServerConfig.applePublicKeyUrl)
        httpGet.addHeader("Content-Type", "application/json")

        val httpClient = HttpClientBuilder.create().build()

        val httpResponse = httpClient.execute(httpGet)!!
        if (httpResponse.statusLine.statusCode != HttpStatus.OK.value()) {
            throw Exception("Apple api response status code is $httpResponse.statusLine.statusCode")
        }
        val responseStringBody = EntityUtils.toString(httpResponse.entity)!!
        return gson.fromJson(responseStringBody, AppleAuthKeysList::class.java)
    }

    @Throws(InvalidKeySpecException::class)
    fun getPublicKey(appleAuthPublicKey: AppleAuthPublicKey): PublicKey {
        val factory = KeyFactory.getInstance(appleAuthPublicKey.kty)!!
        val modulusByte = Base64.getUrlDecoder().decode(appleAuthPublicKey.n)

        val modulus = BigInteger(1, modulusByte)
        val exponentByte = Base64.getUrlDecoder().decode(appleAuthPublicKey.e)
        val exponent = BigInteger(1, exponentByte)

        val spec = RSAPublicKeySpec(modulus, exponent)
        return factory.generatePublic(spec)
    }

    fun verifyCredentials(appleAuthCredentials: AppleAuthCredentials): AppleVerifyCredentialsResponse {

        val appleVerifyCredentialsResponse = AppleVerifyCredentialsResponse(false, "")
        val appleAuthPublicKeyList = try {
            getApplePublicKeyInfo()
        } catch (exception: Exception) {
            appleVerifyCredentialsResponse.errorMessage = exception.message!!
            null
        }
        if (appleAuthPublicKeyList == null) {
            return appleVerifyCredentialsResponse
        } else {
            val appleAuthPublicKey = appleAuthPublicKeyList.keys[0]
            val publicKey = getPublicKey(appleAuthPublicKey)
            try {
                val claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(String(Base64.getDecoder().decode(appleAuthCredentials.identityToken)))
                    .body
                if (claims.subject == appleAuthCredentials.user) {
                    appleVerifyCredentialsResponse.isValid = true
                    appleVerifyCredentialsResponse.errorMessage = ""
                    return appleVerifyCredentialsResponse
                }
            }catch(exception:Exception){
                appleVerifyCredentialsResponse.isValid = false
                appleVerifyCredentialsResponse.errorMessage = exception.message!!
                return appleVerifyCredentialsResponse
            }
        }
        return appleVerifyCredentialsResponse
    }
}