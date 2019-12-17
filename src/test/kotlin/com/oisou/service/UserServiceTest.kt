// package com.oisou.service
//
// import com.oisou.signupsignin.dao.UserRepository
// import com.oisou.signupsignin.model.User
// import com.oisou.signupsignin.model.GenderEnum
// import io.mockk.every
// import io.mockk.mockk
// import org.junit.Assert
// import org.junit.Before
// import org.junit.Test
// import java.util.Optional
// import javax.persistence.EntityNotFoundException
//
// class UserServiceTest {
//
//    private val usersRepository = mockk<UserRepository>()
//    private val userService = UserService(usersRepository)
//
//    private val savedUser = User(999L, "Saved")
//    @Before
//    fun init() {
//        every { usersRepository.findByUsername("username") } returns User(0L, "username")
//        every { usersRepository.findById(111L) } returns Optional.of(User(111L, "username"))
//        every { usersRepository.findById(222L) } throws EntityNotFoundException()
//        every { usersRepository.save(User(111L, "userName",GenderEnum.FEMALE)) } returns User(111L, "userName",GenderEnum.FEMALE)
//        every { usersRepository.save(User(111L, "updatedFound",GenderEnum.FEMALE)) } returns User(111L, "updatedFound",GenderEnum.FEMALE)
//        every { usersRepository.save(savedUser) } returns savedUser
//        every { usersRepository.getOne(111L) } returns User(111L, "found",GenderEnum.FEMALE)
//        every { usersRepository.getOne(222L) } throws EntityNotFoundException()
//    }
//
//    @Test
//    fun `when finding user by id return user`() {
//        // WHEN
//        val user = userService.findUserByID(111L)
//
//        // THEN
//        Assert.assertEquals(user.get().id, 111L)
//    }
//
//    @Test(expected = EntityNotFoundException::class)
//    fun `when finding user by id, user not found`() {
//        // WHEN
//        userService.findUserByID(222L)
//    }
//
//    @Test
//    fun `when creating a user return user`() {
//        val auxUser = userService.createUser(savedUser)
//        Assert.assertEquals(auxUser, savedUser)
//        Assert.assertEquals(auxUser.username, savedUser.username)
//    }
//
//    @Test(expected = EntityNotFoundException::class)
//    fun `when update user throw exception`() {
//        userService.updateUser(User(222L, "",GenderEnum.FEMALE))
//    }
//
//    @Test
//    fun `when updating user return user `() {
//        val auxUser = userService.updateUser(User(111L, "updatedFound",GenderEnum.FEMALE))
//        Assert.assertEquals(auxUser.username, "updatedFound")
//    }
// }
