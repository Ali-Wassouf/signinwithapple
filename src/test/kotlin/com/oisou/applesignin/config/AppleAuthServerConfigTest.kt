package com.oisou.applesignin.config

import com.oisou.config.apple.AppleAuthServerConfig
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AppleAuthServerConfig::class])
class AppleAuthServerConfigTest {

    @Autowired
    lateinit var appleAuthServerConfig: AppleAuthServerConfig

    @Test
    fun testNotNullBean() {
        Assert.assertNotNull(appleAuthServerConfig)
        Assert.assertEquals("https://www.something.com", appleAuthServerConfig.applePublicKeyUrl)
    }
}
