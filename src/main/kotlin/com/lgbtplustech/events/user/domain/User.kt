package com.lgbtplustech.events.user.domain

import java.time.Instant
import java.util.UUID

data class User(
    val id: UUID,
    val email: String,
    val displayName: String,
    val firstName: String,
    val lastName: String,
    val role: UserRole,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    init {
        require(email.isNotBlank()) {
            "Email must not be blank"
        }

        require(firstName.isNotBlank()) {
            "First name must not be blank"
        }

        require(lastName.isNotBlank()) {
            "Last name must not be blank"
        }

        require(displayName.isNotBlank()) {
            "Display name must not be blank"
        }
    }

    companion object {
        fun create(
            id: UUID,
            email: String,
            displayName: String,
            firstName: String,
            lastName: String,
            now: Instant
        ): User =
            User(
                id = id,
                email = email,
                displayName = displayName,
                firstName = firstName,
                lastName = lastName,
                role = UserRole.MEMBER,
                createdAt = now,
                updatedAt = now
            )
    }
}
