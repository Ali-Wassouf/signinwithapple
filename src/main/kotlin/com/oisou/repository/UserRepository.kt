package com.oisou.repository

import com.oisou.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(userName: String): User?
    fun existsByUsername(userName: String): Boolean
    @Transactional
    fun deleteByUsername(userName: String)
}
