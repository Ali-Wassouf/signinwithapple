package com.oisou.service

import com.oisou.datatransferobject.AuthResponseDTO
import com.oisou.model.AuthKey
import com.oisou.model.Role
import com.oisou.model.User
import com.oisou.model.UserAuthRequest
import com.oisou.repository.AuthKeyRepository
import com.oisou.security.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class AuthKeysService(private var authKeyRepository: AuthKeyRepository,
                      private val jwtTokenProvider: JwtTokenProvider) {

    fun createToken(userAuthRequest: UserAuthRequest): AuthResponseDTO {
        val appleCredentials = userAuthRequest.appleCredentials
        val (accessToken, expiresIn) =
            jwtTokenProvider.createAccessToken(appleCredentials.user, Role.ROLE_CLIENT)
        val refreshToken = jwtTokenProvider.createRefreshToken(appleCredentials.user)

        val authKey = AuthKey(1, refreshToken, userAuthRequest.provider, true)
        authKeyRepository.save(authKey)

        return AuthResponseDTO(accessToken, expiresIn, refreshToken, true)

    }

    fun createSignInToken(user : User): AuthResponseDTO {
        val (accessToken, expiresIn) =
            jwtTokenProvider.createAccessToken(user.username, Role.ROLE_CLIENT)
        return AuthResponseDTO(accessToken, expiresIn, user.authKey.refreshToken, false)
    }

    fun validateRefreshToken(user: User): Boolean {
        return user.authKey.isValid!!
    }

    fun invalidateKey(id: Long) {
        val authKey = authKeyRepository.getOne(id)
        authKey.isValid = false;
        authKeyRepository.save(authKey)
    }
}