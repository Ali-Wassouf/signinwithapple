package com.oisou.controller

import com.oisou.controller.message.ErrorMessage
import com.oisou.model.User
import com.oisou.service.AuthKeysService
import com.oisou.service.UserService
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
import javax.servlet.http.HttpServletResponse



@RestController
@RequestMapping("/users/v1")
class UserController constructor(private val userService: UserService, private val authKeysService: AuthKeysService) {

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
    fun createUser(/*@RequestBody user: User,*/
                   @RequestHeader(name = "Auth") authorization: String): ResponseEntity<Any> {
        return when (authKeysService.validateRefreshToken(authorization)) {
            true -> {
                //val userNew = userService.createUser(user)
                ResponseEntity("", HttpStatus.OK)
            }
            else -> {
                ResponseEntity("", HttpStatus.UNAUTHORIZED)
            }
        }
    }

    @ExceptionHandler(Exception::class)
    @Throws(IOException::class)
    fun springHandleNotFound(ex:Exception):ResponseEntity<ErrorMessage> {
        return ResponseEntity(ErrorMessage(ex.message!!), HttpStatus.INTERNAL_SERVER_ERROR)
    }

}
