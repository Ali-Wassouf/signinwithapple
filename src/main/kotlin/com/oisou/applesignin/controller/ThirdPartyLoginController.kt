package com.oisou.applesignin.controller

import com.oisou.applesignin.model.AppleVerifyCredentialsResponse
import com.oisou.applesignin.model.ProviderEnum
import com.oisou.applesignin.model.UserAuthRequest
import com.oisou.applesignin.service.AppleAuthService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value = "Third party signup/signin handler", description = "This controller is responsible for verifying the third party user, i.e if the sent information matches the " +
    "information on the auth provider")
@RestController
@RequestMapping("/api/v1/auth")
class ThirdPartyLoginController(val appleAuthService: AppleAuthService) {

    @ApiOperation(value = "For now, this endpoint verifies if the sent information matches the information on the auth provider server", response =
    AppleVerifyCredentialsResponse::class)
    @PostMapping("/issue")
    fun verifyCredentials(@RequestBody userAuthRequest: UserAuthRequest): AppleVerifyCredentialsResponse {
        if (userAuthRequest.provider == ProviderEnum.APPLE)
            return appleAuthService.verifyCredentials(userAuthRequest.appleCredentials)
        return AppleVerifyCredentialsResponse(false, "")
    }
}