package com.oisou.service

import com.oisou.datatransferobject.AuthResponseDTO
import com.oisou.model.AuthKey
import com.oisou.model.Role
import com.oisou.model.User
import com.oisou.model.UserAuthRequest
import com.oisou.repository.AuthKeyRepository
import com.oisou.security.JwtTokenProvider
import org.springframework.stereotype.Service
import java.sql.Timestamp
import javax.persistence.EntityNotFoundException

@Service
class AuthKeysService(private var authKeyRepository: AuthKeyRepository,
                      private val jwtTokenProvider: JwtTokenProvider) {

    fun createToken(userAuthRequest: UserAuthRequest): AuthResponseDTO {
        val appleCredentials = userAuthRequest.appleCredentials
        val (accessToken, expiresIn) =
            jwtTokenProvider.createAccessToken(appleCredentials.user, Role.ROLE_CLIENT)
        val refreshToken = jwtTokenProvider.createRefreshToken(appleCredentials.user)

        val authKey = AuthKey(1, refreshToken, userAuthRequest.provider, true, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
        authKeyRepository.save(authKey)

        return AuthResponseDTO(accessToken, expiresIn, refreshToken, true)

    }

    fun createSignInToken(user: User): AuthResponseDTO {
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

    fun validateRefreshToken(token: String): Pair<Boolean, AuthKey> {
        val newToken = jwtTokenProvider.resolveToken(token)
        val authKey = authKeyRepository.findByRefreshToken(newToken) ?: throw EntityNotFoundException("Token not found")
        return when (authKey.isValid) {
            true -> {
                when (jwtTokenProvider.validateToken(newToken)) {
                    true -> Pair(true, authKey)
                    else -> Pair(false, authKey)
                }
            }
            else -> {
                Pair(false, authKey)
            }
        }
    }
}