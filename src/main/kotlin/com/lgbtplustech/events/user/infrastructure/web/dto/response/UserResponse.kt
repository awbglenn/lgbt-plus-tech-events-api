package com.lgbtplustech.events.user.infrastructure.web.dto.response

import com.lgbtplustech.events.user.domain.User
import java.time.Instant
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val email: String,
    val displayName: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun User.toResponse() = UserResponse(
    id = id,
    email = email,
    displayName = displayName,
    firstName = firstName,
    lastName = lastName,
    role = role.name,
    createdAt = createdAt,
    updatedAt = updatedAt
)
