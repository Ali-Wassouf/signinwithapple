package com.oisou.repository

import com.oisou.model.AuthKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthKeyRepository : JpaRepository<AuthKey, Long> {
    fun findByRefreshToken(token: String): AuthKey?
}


