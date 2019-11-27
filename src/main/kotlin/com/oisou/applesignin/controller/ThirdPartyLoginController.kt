package com.oisou.applesignin.controller

import com.oisou.applesignin.service.AppleAuthService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/login")
class ThirdPartyLoginController(val appleAuthService: AppleAuthService){
    //TODO write end point for apple auth
}