package com.oisou.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "security.jwt.token")
class SecurityConfigRepository {
    var secretKey = "AliMOJOMat"
    var validityInMilliseconds = 3580L
}