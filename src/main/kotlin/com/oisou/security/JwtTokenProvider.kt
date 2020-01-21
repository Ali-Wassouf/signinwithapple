package com.oisou.security

import com.oisou.config.SecurityConfigRepository
import com.oisou.exception.TokenValidationException
import com.oisou.model.Role
import com.oisou.service.CustomUserDetailsService
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.util.Base64
import java.util.Date
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(val customUserDetailsService: CustomUserDetailsService, val securityConfigRepository: SecurityConfigRepository) {

    fun createAccessToken(username: String, role: Role): Pair<String, Long> {
        val claims = Jwts.claims().setSubject(username)
        claims["auth"] = SimpleGrantedAuthority(role.authority)

        val date = Date()
        val validity = Date(date.time + securityConfigRepository.validityInMilliseconds)
        val encryptedSecret = Base64.getEncoder().encodeToString(securityConfigRepository.secretKey.toByteArray())
        val accessToken = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(date)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, encryptedSecret).compact()
        return Pair(accessToken, validity.time)
    }

    fun createRefreshToken(username: String):String{
        val claims = Jwts.claims().setSubject(username+"OisoURefresh")
        val date = Date()
        val validity = Date(date.time + securityConfigRepository.validityInMilliseconds)
        val encryptedSecret = Base64.getEncoder().encodeToString(securityConfigRepository.secretKey.toByteArray())
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(date)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, encryptedSecret).compact()
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
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun validateToken(token: String):Boolean{
        try {
            Jwts.parser().setSigningKey(securityConfigRepository.secretKey).parseClaimsJws(token)
            return true
        } catch (e: JwtException) {
            throw TokenValidationException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: IllegalArgumentException) {
            throw TokenValidationException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

}