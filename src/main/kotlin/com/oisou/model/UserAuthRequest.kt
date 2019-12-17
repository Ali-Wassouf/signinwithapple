package com.oisou.model

import com.oisou.model.apple.AppleAuthCredentials

data class UserAuthRequest(
    val provider: ProviderEnum,
    val appleCredentials: AppleAuthCredentials
)