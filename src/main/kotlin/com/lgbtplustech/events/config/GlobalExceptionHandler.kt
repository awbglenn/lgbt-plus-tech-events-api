package com.lgbtplustech.events.config

import com.lgbtplustech.events.event.application.exception.EventCannotBePublishedException
import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException::class)
    fun handleEventNotFound(
        exception: EventNotFoundException
    ): ProblemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            exception.message ?: "Event not found"
        ).apply {
            title = "Event not found"
        }

    @ExceptionHandler(EventCannotBePublishedException::class)
    fun handleEventCannotBePublished(
        exception: EventCannotBePublishedException
    ): ProblemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.CONFLICT,
            exception.message ?: "Event cannot be published"
        ).apply {
            title = "Event cannot be published"
        }
}