package com.oisou.signupsignin.dao

import com.oisou.signupsignin.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(userName: String): User
}
