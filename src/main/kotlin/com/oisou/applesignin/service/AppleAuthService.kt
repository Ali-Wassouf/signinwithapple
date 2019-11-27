package com.oisou.applesignin.service

import com.google.gson.Gson
import com.oisou.applesignin.config.AppleAuthServerConfig
import com.oisou.applesignin.model.AppleAuthKeysList
import com.oisou.applesignin.model.AppleAuthPublicKey
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
    private val appleAuthServerConfig: AppleAuthServerConfig,
    private val gson: Gson
) {
    fun getApplePublicKey(): AppleAuthKeysList {
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
}