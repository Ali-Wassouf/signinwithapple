package com.oisou.service

import com.oisou.datatransferobject.AuthResponseDTO
import com.oisou.model.User
import com.oisou.repository.AuthKeyRepository
import com.oisou.repository.GenderRepository
import com.oisou.repository.UserRepository
import com.oisou.security.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService constructor(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder, private
val jwtTokenProvider:  JwtTokenProvider) {

    fun findUserByID(id: Long) = userRepository.findById(id)

    fun createUser(user: User): User {
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    fun findByUserName(username: String) = userRepository.findByUsername(username)!!

    fun deleteUserById(id: Long) = userRepository.deleteById(id)

    fun updateUser(user: User): User {
        var existingUser = userRepository.getOne(user.id)
        existingUser = existingUser.copy(username = user.username)
        return userRepository.save(existingUser)
    }

    fun isNewUser(username: String) = !userRepository.existsByUsername(username)

//TODO
//    fun signUserUp(appleCredentials: AppleAuthCredentials): AuthResponseDTO {
//        var user = User()
//        user.password = passwordEncoder.encode(user.password)
//
//        val (accessToken, expiresIn) = jwtTokenProvider.createAccessToken(appleCredentials.user, Role.ROLE_CLIENT)
//        val refreshToken = jwtTokenProvider.createRefreshToken(appleCredentials.user)
//        user.refreshToken = refreshToken
//        user = userRepository.save(user)
//        return AuthResponseDTO(accessToken, expiresIn, refreshToken, true)
//    }
    fun findRefreshToken() {
        //TODO find refresh token for the user by user name and
        // create an access token for the user
        // return the same dto for the sign up sign in

    }
}
