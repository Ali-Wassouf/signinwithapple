package com.oisou.repository

import com.oisou.EnableEmbeddedPostgres
import com.oisou.model.AuthKey
import com.oisou.model.Gender
import com.oisou.model.ProviderEnum
import com.oisou.model.Role
import com.oisou.model.User
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.sql.Timestamp
import java.util.Date
import kotlin.random.Random

@RunWith(SpringJUnit4ClassRunner::class)
@EnableEmbeddedPostgres
@Component
@TestPropertySource(locations = ["classpath:application-test.properties"])
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `when saving a user to the db, finding the user returns the same user`() {
        val user = User(Random.nextLong(), "Username", Date(), "syr", "1.0",
            "someid", "appleUserId", Date(), Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()),
            Date(), false, "", Role.ROLE_CLIENT, Gender(Random.nextLong(), ""), Gender(Random.nextLong(), ""),
            AuthKey(Random.nextLong(), "", ProviderEnum.APPLE, true), "")

        userRepository.save(user)
        Assert.assertTrue(userRepository.existsByUsername("Username"))
    }
}