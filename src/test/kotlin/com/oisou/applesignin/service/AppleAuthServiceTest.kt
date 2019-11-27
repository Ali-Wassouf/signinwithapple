package com.oisou.applesignin.service

import com.oisou.applesignin.config.AppleAuthServerConfig
import com.oisou.applesignin.model.AppleAuthPublicKey
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Base64

@RunWith(MockitoJUnitRunner::class)
class AppleAuthServiceTest {

    private val appleAuthServerConfig = mockk<AppleAuthServerConfig>()


    private val appleAuthService = AppleAuthService(appleAuthServerConfig)

    @Before
    fun init() {
        every { appleAuthServerConfig.applePublicKeyUrl }.returns("https://appleid.apple.com/auth/keys")
    }

    @Test
    fun `when calling the apple public key api return correctly parsed response`() {

        val appleAuthKeysList = appleAuthService.getApplePublicKeyInfo()
        Assert.assertNotNull(appleAuthKeysList)
        Assert.assertNotNull(appleAuthKeysList.keys[0].alg)
        Assert.assertNotNull(appleAuthKeysList.keys[0].e)
        Assert.assertNotNull(appleAuthKeysList.keys[0].kid)
        Assert.assertNotNull(appleAuthKeysList.keys[0].kty)
        Assert.assertNotNull(appleAuthKeysList.keys[0].n)
        Assert.assertNotNull(appleAuthKeysList.keys[0].use)
    }

    @Test
    fun `when generating a public key given a response return valid key`() {
        val appleAuthPublicKey = AppleAuthPublicKey("RSA", "AIDOPK1", "sig", "RS256",
            "lxrwmuYSAsTfn-lUu4goZSXBD9ackM9OJuwUVQHmbZo6GW4Fu_auUdN5zI7Y1dEDfgt7m7QXWbHuMD01HLnD4eRtY-RNwCWdjNfEaY_esUPY3OVMrNDI15Ns13xspWS3q" +
                "-13kdGv9jHI28P87RvMpjz_JCpQ5IM44oSyRnYtVJO-320SB8E2Bw92pmrenbp67KRUzTEVfGU4-obP5RZ09OxvCr1io4KJvEOjDJuuoClF66AT72WymtoMdwzUmhINjR0XSqK6H0MdWsjw7ysyd_JhmqX5CAaT9Pgi0J8lU_pcl215oANqjy7Ob-VMhug9eGyxAWVfu_1u6QJKePlE-w", "AQAB")
        Assert.assertNotNull(appleAuthService.getPublicKey(appleAuthPublicKey))
    }
}