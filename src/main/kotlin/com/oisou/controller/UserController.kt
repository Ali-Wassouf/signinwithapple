package com.oisou.controller

import com.oisou.model.User
import com.oisou.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users/v1")
class UserController constructor(private val userService: UserService) {

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
}
