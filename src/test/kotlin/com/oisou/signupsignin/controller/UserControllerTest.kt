package com.oisou.signupsignin.controller

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UserControllerTest{
    @Autowired
    private lateinit var userController: UserController
    @Test
    fun contextLoad() {
        Assert.assertNotNull(userController)
    }

}
