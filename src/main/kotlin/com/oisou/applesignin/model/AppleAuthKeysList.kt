package com.oisou.applesignin.model

import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
class AppleAuthKeysList {
    lateinit var keys: List<AppleAuthPublicKey>
}