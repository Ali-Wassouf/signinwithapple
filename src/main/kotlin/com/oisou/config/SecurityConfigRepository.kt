package com.oisou.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "security.jwt.token")
class SecurityConfigRepository {
    var secretKey = ""
    var validityInMilliseconds = 0L
}