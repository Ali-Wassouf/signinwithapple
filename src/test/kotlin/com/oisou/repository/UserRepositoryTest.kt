package com.oisou.repository

//import com.oisou.utils.EnableEmbeddedPostgres
import com.oisou.model.AuthKey
import com.oisou.model.Gender
import com.oisou.model.ProviderEnum
import com.oisou.model.Role
import com.oisou.model.User
import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
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
@Component
@TestPropertySource(locations = ["classpath:application-test.properties"])
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var authKeyRepository: AuthKeyRepository

    @Test
    fun `when saving a user to the db, finding the user returns the same user`() {
        val geometryFactory = GeometryFactory()

        val coordinate = Coordinate()
        coordinate.x = 2.0
        coordinate.y = 5.0

        val myPoint = geometryFactory.createPoint(coordinate)
        val user = User(Random.nextLong(), "Username", Date(), "syr", "1.0", myPoint,
            Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()),
            false, "", Role.ROLE_CLIENT,
            Gender(Random.nextLong(), "", "", true, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())),
            Gender(Random.nextLong(), "", "", true, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())),
            AuthKey(Random.nextLong(), "", ProviderEnum.APPLE, true, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())),
            "", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))

        userRepository.save(user)
        userRepository.findByUsername("Username")
        Assert.assertTrue(userRepository.existsByUsername("Username"))
    }

    @Test
    fun `when saving a user to the db, with existing authtoken, finding the user returns the same user with the correct token`() {

        val authkey = AuthKey(Random.nextLong(), "testtest", ProviderEnum.APPLE, true, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
        authKeyRepository.save(authkey)

        val user = User(Random.nextLong(), "Username_", Date(), "syr", "1.0", null,
            Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()),
            false, "", Role.ROLE_CLIENT,
            Gender(Random.nextLong(), "", "", true, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())),
            Gender(Random.nextLong(), "", "", true, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())),
            authKeyRepository.findByRefreshToken("testtest")!!,
            "", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))

        userRepository.save(user)
        val savedUser = userRepository.findByUsername("Username_")
        Assert.assertTrue(userRepository.existsByUsername("Username_"))
        Assert.assertEquals(authkey.refreshToken, savedUser!!.authKey.refreshToken)
    }
}