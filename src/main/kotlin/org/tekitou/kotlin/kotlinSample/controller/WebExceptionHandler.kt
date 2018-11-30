package org.tekitou.kotlin.kotlinSample.controller

import mu.KLogging
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class WebExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): String {
        logger.info("ex", exception)
        return exception.toString()
    }

    companion object : KLogging()
}