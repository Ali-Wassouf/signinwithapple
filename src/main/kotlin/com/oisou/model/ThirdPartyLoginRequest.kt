package com.oisou.model

data class ThirdPartyLoginRequest(var provider: String, var userId: String, var identityToken: String)
