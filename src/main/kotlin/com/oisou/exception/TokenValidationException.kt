package com.oisou.exception

import org.springframework.http.HttpStatus

data class TokenValidationException(override var message: String, var httpStatus: HttpStatus) : RuntimeException()