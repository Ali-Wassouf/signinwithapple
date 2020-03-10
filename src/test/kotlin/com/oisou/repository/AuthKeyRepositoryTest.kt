package com.oisou.repository

import com.oisou.utils.EnableEmbeddedPostgres
import com.oisou.model.AuthKey
import com.oisou.model.ProviderEnum
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.sql.Timestamp
import java.util.Random

@RunWith(SpringJUnit4ClassRunner::class)
@EnableEmbeddedPostgres
@Component
@TestPropertySource(locations = ["classpath:application-test.properties"])
class AuthKeyRepositoryTest {

    @Autowired
    private lateinit var authKeyRepository: AuthKeyRepository

    @Test
    fun `when saving an auth key, finding this key by token should return true`() {
        authKeyRepository.save(AuthKey(Random().nextLong(), "sasa", ProviderEnum.APPLE, true, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())))
        Assert.assertTrue(authKeyRepository.findByRefreshToken("sasa") != null)

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:dao/INSERT.sql"])
    fun `when finding authkey by refreshtoken, return correct authkey`() {
        Assert.assertTrue(authKeyRepository.findByRefreshToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMDAyOTUuMGE0OWEyYmY4MTM3NDc5MThiZDRhOTI1YjdiMmFjZjAuMDgyME9pc29VUmVmcmVzaCIsImlhdCI6MTU4MTE5NDQ3OCwiZXhwIjoxNTgxMTk4MDc4fQ.c8E_Sb0hPeSiUO3rcCySE_7cWgub2TGS2xg95RBqUyM") != null)
    }
}