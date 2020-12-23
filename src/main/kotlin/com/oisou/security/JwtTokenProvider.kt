package com.oisou.security

import com.oisou.config.SecurityConfigRepository
import com.oisou.exception.TokenValidationException
import com.oisou.model.Role
import com.oisou.service.CustomUserDetailsService
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date
import javax.servlet.http.HttpServletRequest

private val logger = KotlinLogging.logger {}

@Component
class JwtTokenProvider(val customUserDetailsService: CustomUserDetailsService, val securityConfigRepository: SecurityConfigRepository) {

    fun createAccessToken(username: String, role: Role): Pair<String, Long> {
        logger.info { "Creating access token in system" }
        val claims = Jwts.claims().setSubject(username)
        claims["auth"] = SimpleGrantedAuthority(role.authority)

        val date = Date()
        val validity = Date(date.time + securityConfigRepository.validityInMilliseconds)
        val accessToken = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(date)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, securityConfigRepository.secretKey).compact()
        logger.info { "Created access token $accessToken" }
        return Pair(accessToken, validity.time)
    }

    fun createRefreshToken(username: String): String {
        logger.info { "Creating refresh token " }
        val claims = Jwts.claims().setSubject(username + "OisoURefresh")
        val date = Date()
        //TODO make validity one year
        val validity = Date(date.time + Long.MAX_VALUE)
        val refreshToken = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(date)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, securityConfigRepository.secretKey).compact()
        logger.info { "Created refresh token $refreshToken" }
        return refreshToken
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = customUserDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(securityConfigRepository.secretKey).parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith( "Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(securityConfigRepository.secretKey).parseClaimsJws(token)
            return true
        } catch (e: JwtException) {
            logger.error { "JWTEXC ${e.stackTrace}" }
            throw TokenValidationException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: IllegalArgumentException) {
            logger.info { "Illegal argument $e" }
            throw TokenValidationException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

}