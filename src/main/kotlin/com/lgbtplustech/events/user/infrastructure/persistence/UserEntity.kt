package com.lgbtplustech.events.user.infrastructure.persistence

import com.lgbtplustech.events.user.domain.User
import com.lgbtplustech.events.user.domain.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    val id: UUID,

    @Column(nullable = false, unique = true, length = 320)
    val email: String,

    @Column(name = "display_name", nullable = false)
    val displayName: String,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant
) {
    fun toDomain() = User(
        id = id,
        email = email,
        displayName = displayName,
        firstName = firstName,
        lastName = lastName,
        role = role,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    companion object {
        fun fromDomain(user: User) = UserEntity(
            id = user.id,
            email = user.email,
            displayName = user.displayName,
            firstName = user.firstName,
            lastName = user.lastName,
            role = user.role,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }
}
