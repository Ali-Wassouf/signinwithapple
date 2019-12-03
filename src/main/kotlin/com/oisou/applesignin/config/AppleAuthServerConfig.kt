package com.oisou.applesignin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class AppleAuthServerConfig(
    @Value("\${apple.auth.server.url}") val applePublicKeyUrl: String
)
