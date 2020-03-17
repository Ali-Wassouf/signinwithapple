package com.oisou.controller

import com.oisou.model.ProviderEnum
import com.oisou.model.UserAuthRequest
import com.oisou.model.apple.AppleVerifyCredentialsResponse
import com.oisou.service.AppleAuthService
import com.oisou.service.AuthKeysService
import com.oisou.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@Api(value = "Third party signup/signin handler", description = "This controller is responsible for verifying the third party user, i.e if the sent information matches the " +
    "information on the auth provider")
@RestController
@RequestMapping("/api/v1/auth")
class ThirdPartyLoginController(private val appleAuthService: AppleAuthService, private val authKeysService: AuthKeysService, private val userService: UserService) {

    @ApiOperation(value = "For now, this endpoint verifies if the sent information matches the information on the auth provider server", response =
    AppleVerifyCredentialsResponse::class)
    @PostMapping("/issue")
    fun verifyCredentials(@RequestBody userAuthRequest: UserAuthRequest): ResponseEntity<Any> {
       logger.info { "Issuing token for user with user ${userAuthRequest.appleCredentials.user} and identity token ${userAuthRequest.appleCredentials.user}"}
        if (userAuthRequest.provider == ProviderEnum.APPLE) {

            val validityResponse = appleAuthService.verifyCredentials(userAuthRequest.appleCredentials)
            return when (validityResponse.isValid) {
                true -> {
                    return when (userService.isNewUser(userAuthRequest.appleCredentials.user)) {
                        true -> {
                            logger.info { "New user, creating user in system" }
                            ResponseEntity(authKeysService.createToken(userAuthRequest), HttpStatus.OK)
                        }
                        else -> {
                            //sign them in
                            val user = userService.findByUserName(userAuthRequest.appleCredentials.user)
                            return when (authKeysService.validateRefreshToken(user)) {
                                true -> {
                                    ResponseEntity(authKeysService.createSignInToken(user), HttpStatus.OK)
                                }
                                else -> {
                                    ResponseEntity("", HttpStatus.BAD_REQUEST)
                                }
                            }
                        }
                    }
                }
                else -> {
                    ResponseEntity(validityResponse.errorMessage, HttpStatus.BAD_REQUEST)
                }
            }

        } else
            return ResponseEntity("Unsupported provider", HttpStatus.BAD_REQUEST)
    }
}