package com.oisou.applesignin.model

data class ThirdPartyLoginRequest(var provider: String, var userId: String, var identityToken: String)
