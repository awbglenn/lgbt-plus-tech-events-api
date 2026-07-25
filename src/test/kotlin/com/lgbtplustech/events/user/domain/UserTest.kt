package com.lgbtplustech.events.user.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.UUID

class UserTest {

    private val now = Instant.parse("2026-07-25T18:00:00Z")

    @Test
    fun `creates a valid user`() {
        val id = UUID.randomUUID()

        val user = User(
            id = id,
            email = "alice@example.com",
            displayName = "Alice",
            firstName = "Alice",
            lastName = "Johnson",
            role = UserRole.MEMBER,
            createdAt = now,
            updatedAt = now
        )

        assertEquals(id, user.id)
        assertEquals("alice@example.com", user.email)
        assertEquals("Alice", user.displayName)
        assertEquals("Alice", user.firstName)
        assertEquals("Johnson", user.lastName)
        assertEquals(UserRole.MEMBER, user.role)
        assertEquals(now, user.createdAt)
        assertEquals(now, user.updatedAt)
    }

    @Test
    fun `does not allow a blank email`() {
        assertThrows(IllegalArgumentException::class.java) {
            User(
                id = UUID.randomUUID(),
                email = " ",
                displayName = "Alice",
                firstName = "Alice",
                lastName = "Johnson",
                role = UserRole.MEMBER,
                createdAt = now,
                updatedAt = now
            )
        }
    }

    @Test
    fun `does not allow a blank display name`() {
        assertThrows(IllegalArgumentException::class.java) {
            User(
                id = UUID.randomUUID(),
                email = "alice@example.com",
                displayName = " ",
                firstName = "Alice",
                lastName = "Johnson",
                role = UserRole.MEMBER,
                createdAt = now,
                updatedAt = now
            )
        }
    }

    @Test
    fun `does not allow a blank first name`() {
        assertThrows(IllegalArgumentException::class.java) {
            User(
                id = UUID.randomUUID(),
                email = "alice@example.com",
                displayName = "Alice",
                firstName = " ",
                lastName = "Johnson",
                role = UserRole.MEMBER,
                createdAt = now,
                updatedAt = now
            )
        }
    }

    @Test
    fun `does not allow a blank last name`() {
        assertThrows(IllegalArgumentException::class.java) {
            User(
                id = UUID.randomUUID(),
                email = "alice@example.com",
                displayName = "Alice",
                firstName = "Alice",
                lastName = " ",
                role = UserRole.MEMBER,
                createdAt = now,
                updatedAt = now
            )
        }
    }
}
