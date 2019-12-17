package com.oisou.service

import com.oisou.repository.UserRepository
import com.oisou.model.User
import java.util.Optional
import org.springframework.stereotype.Service

@Service
class UserService constructor(private val userRepository: UserRepository) {

    fun findUserByID(id: Long): Optional<User> {
        return userRepository.findById(id)
    }

    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun deleteUserById(id: Long) {
        userRepository.deleteById(id)
    }

    fun updateUser(user: User): User {
        var existingUser = userRepository.getOne(user.id)
        existingUser = existingUser.copy(username = user.username)
        return userRepository.save(existingUser)
    }
}
