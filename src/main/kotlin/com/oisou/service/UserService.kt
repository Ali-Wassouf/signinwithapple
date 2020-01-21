package com.oisou.service

import com.oisou.model.User
import com.oisou.model.apple.AppleAuthCredentials
import com.oisou.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService constructor(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun findUserByID(id: Long) = userRepository.findById(id)

    fun createUser(user: User) = userRepository.save(user)

    fun findByUserName(username: String) = userRepository.findByUsername(username)

    fun deleteUserById(id: Long) = userRepository.deleteById(id)

    fun updateUser(user: User): User {
        var existingUser = userRepository.getOne(user.id)
        existingUser = existingUser.copy(username = user.username)
        return userRepository.save(existingUser)
    }

    fun isNewUser(username: String) = userRepository.existsByUsername(username)

    fun signUserUp(appleCredentials: AppleAuthCredentials) {

    }
}
