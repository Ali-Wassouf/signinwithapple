package com.oisou.exception

import java.util.Date
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundExceptionHandler(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(Date(), ex.message!!, request.getDescription(false))
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(ex: Exception, request: WebRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(Date(), ex.message!!, request.getDescription(false))
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }
}
