package com.lgbtplustech.events.user.infrastructure.web.controller

import com.lgbtplustech.events.user.application.port.inbound.command.CreateUserCommand
import com.lgbtplustech.events.user.application.port.inbound.CreateUser
import com.lgbtplustech.events.user.infrastructure.web.dto.request.CreateUserRequest
import com.lgbtplustech.events.user.infrastructure.web.dto.response.CreateUserResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserCommandController(
    private val createUser: CreateUser
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody request: CreateUserRequest
    ): CreateUserResponse {
        val user = createUser.execute(
            CreateUserCommand(
                email = request.email,
                displayName = request.displayName,
                firstName = request.firstName,
                lastName = request.lastName
            )
        )

        return CreateUserResponse(user.id)
    }
}
