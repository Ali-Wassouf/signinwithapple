package com.oisou.applesignin.model

data class UserAuthRequest(
    val provider: ProviderEnum,
    val appleCredentials: AppleAuthCredentials
)