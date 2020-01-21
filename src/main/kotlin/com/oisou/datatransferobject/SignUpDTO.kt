package com.oisou.datatransferobject

data class SignUpDTO(private val accessToken: String,
                     private val userId: Long,
                     private val expiresIn: Long,
                     private val refreshToken: String,
                     private val isNewUser: Boolean)