package com.oisou.applesignin.controller

import com.oisou.applesignin.model.AppleAuthUserInfo
import com.oisou.applesignin.service.AppleAuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class ThirdPartyLoginController(val appleAuthService: AppleAuthService) {

    @PostMapping("/issue")
    fun verifyCredentials(@RequestBody appleAuthUserInfo: AppleAuthUserInfo) = appleAuthService.verifyCredentials(appleAuthUserInfo)
}
