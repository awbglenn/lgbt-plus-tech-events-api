package com.lgbtplustech.events.config

import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEventNotFound(exception: EventNotFoundException) =
        mapOf("message" to exception.message)
}