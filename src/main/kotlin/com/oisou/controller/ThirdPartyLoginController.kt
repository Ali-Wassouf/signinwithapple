package com.oisou.controller

import com.oisou.model.ProviderEnum
import com.oisou.model.UserAuthRequest
import com.oisou.model.apple.AppleVerifyCredentialsResponse
import com.oisou.service.AppleAuthService
import com.oisou.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value = "Third party signup/signin handler", description = "This controller is responsible for verifying the third party user, i.e if the sent information matches the " +
    "information on the auth provider")
@RestController
@RequestMapping("/api/v1/auth")
class ThirdPartyLoginController(private val appleAuthService: AppleAuthService, private val userService: UserService) {

    @ApiOperation(value = "For now, this endpoint verifies if the sent information matches the information on the auth provider server", response =
    AppleVerifyCredentialsResponse::class)
    @PostMapping("/issue")
    fun verifyCredentials(@RequestBody userAuthRequest: UserAuthRequest): ResponseEntity<Any> {
        if (userAuthRequest.provider == ProviderEnum.APPLE) {
            //TODO
            // appleAuthService.verifyCredentials(userAuthRequest.appleCredentials)
            // check validity and proceed
            //check if user exists in our db
            // if user new -> create a user with fixed fields and save it in db with an id
            //
            //

            var validityResponse = appleAuthService.verifyCredentials(userAuthRequest.appleCredentials)
            return when (validityResponse.isValid) {
                true -> {
                    //if user exist sign them in
                    //else sign them up
                    when (userService.isNewUser(userAuthRequest.appleCredentials.user)) {
                        true -> {
                            //Sign them in
                        }
                        else -> {
                            return ResponseEntity(userService.signUserUp(userAuthRequest.appleCredentials), HttpStatus.OK)
                        }

                    }

                    ResponseEntity("Success", HttpStatus.OK)
                }
                else -> {
                    ResponseEntity(validityResponse.errorMessage, HttpStatus.BAD_REQUEST)
                }
            }

        } else
            return ResponseEntity("Unsupported provider", HttpStatus.BAD_REQUEST)
    }
}