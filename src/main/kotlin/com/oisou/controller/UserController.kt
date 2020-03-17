package com.oisou.controller

import com.oisou.controller.message.ErrorMessage
import com.oisou.model.Role
import com.oisou.model.User
import com.oisou.model.payload.UserPayload
import com.oisou.service.AuthKeysService
import com.oisou.service.GenderService
import com.oisou.service.UserService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.sql.Timestamp

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/users")
class UserController constructor(private val userService: UserService, private val genderService: GenderService, private val authKeysService: AuthKeysService) {

    @GetMapping("/{userId}")
    fun getDriverById(@PathVariable userId: Long): ResponseEntity<User> {
        val foundUser = userService.findUserByID(userId)
        return if (foundUser.isPresent)
            ResponseEntity(foundUser.get(), HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{userId}")
    fun updateUserById(@PathVariable userId: Long, @RequestBody user: User): ResponseEntity<User> {
        return ResponseEntity(userService.updateUser(user), HttpStatus.OK)
    }

    @DeleteMapping("/{userId}")
    fun deleteUserById(@PathVariable userId: Long) {
        userService.deleteUserById(userId)
    }

    @PostMapping("/create")
    fun createUser(@RequestBody user: UserPayload,
                   @RequestHeader(name = "Auth") authorization: String): ResponseEntity<Any> {
        val validatePair = authKeysService.validateRefreshToken(authorization)

        logger.info { "Creating user using provider userId ${user.authProviderId}" }
        return when (validatePair.first) {
            true -> {
                val gender = genderService.findGender(user.genderId)
                val genderOfInterest = genderService.findGender(user.genderOfInterestId)
                val newUser = User(1L, user.authProviderId, user.dateOfBirth, user.countryCode, user.appVersion,null, Timestamp(System.currentTimeMillis()), null,
                    false, user.authProviderToken, Role.ROLE_CLIENT, gender, genderOfInterest, validatePair.second, user.name, Timestamp(System.currentTimeMillis()),
                    Timestamp(System.currentTimeMillis()))
                userService.createUser(newUser)
                ResponseEntity("", HttpStatus.OK)
            }
            else -> {
                ResponseEntity("", HttpStatus.UNAUTHORIZED)
            }
        }
    }

    @ExceptionHandler(Exception::class)
    @Throws(IOException::class)
    fun springHandleNotFound(ex: Exception): ResponseEntity<ErrorMessage> {
        return ResponseEntity(ErrorMessage(ex.message!!), HttpStatus.INTERNAL_SERVER_ERROR)
    }

}
