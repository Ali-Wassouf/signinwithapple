package com.oisou.signupsignin.service

import com.oisou.signupsignin.dao.UserRepository
import com.oisou.signupsignin.domain.User
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
