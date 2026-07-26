package com.lgbtplustech.events.user.infrastructure.web.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val displayName: String,

    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String
)
