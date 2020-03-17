package com.oisou.service

import com.oisou.datatransferobject.AuthResponseDTO
import com.oisou.model.AuthKey
import com.oisou.model.Role
import com.oisou.model.User
import com.oisou.model.UserAuthRequest
import com.oisou.repository.AuthKeyRepository
import com.oisou.security.JwtTokenProvider
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.sql.Timestamp
import javax.persistence.EntityNotFoundException

private val logger = KotlinLogging.logger {}

@Service
class AuthKeysService(private var authKeyRepository: AuthKeyRepository,
                      private val jwtTokenProvider: JwtTokenProvider) {

    fun createToken(userAuthRequest: UserAuthRequest): AuthResponseDTO {
        logger.info { "Creating user ${userAuthRequest.appleCredentials.user} and token ${userAuthRequest.appleCredentials.identityToken}" }
        val appleCredentials = userAuthRequest.appleCredentials
        val (accessToken, expiresIn) =
            jwtTokenProvider.createAccessToken(appleCredentials.user, Role.ROLE_CLIENT)
        val refreshToken = jwtTokenProvider.createRefreshToken(appleCredentials.user)

        val authKey = AuthKey(1L, refreshToken, userAuthRequest.provider, true, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
        logger.info { " Created auth keys $authKey " }
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
        logger.info { "Validating refresh token $token" }
        val authKey = authKeyRepository.findByRefreshToken(token) ?: throw EntityNotFoundException("Token not found")
        logger.info { "Refresh token is found" }
        return when (authKey.isValid) {
            true -> {
                logger.info { "Token is valid" }
                when (jwtTokenProvider.validateToken(token)) {
                    true -> {
                        logger.info { "Token parsed successfully" }
                        Pair(true, authKey)
                    }
                    else -> {
                        logger.info { "Parsing token failed" }
                        Pair(false, authKey)
                    }
                }
            }
            else -> {
                Pair(false, authKey)
            }
        }
    }
}