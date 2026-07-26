package com.lgbtplustech.events.user.infrastructure.persistence

import com.lgbtplustech.events.user.domain.User
import com.lgbtplustech.events.user.domain.UserRole
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import java.time.Instant
import java.util.UUID

@DataJpaTest
@Testcontainers
@Import(PostgresUserRepository::class)
class PostgresUserRepositoryTest(
    @Autowired private val repository: PostgresUserRepository,
) {

    companion object {
        @Container
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:17")
    }

    @Test
    fun `saves and retrieves a user by email`() {
        val user = User.create(
            id = UUID.randomUUID(),
            email = "alex@example.com",
            displayName = "Alex",
            firstName = "Alex",
            lastName = "Taylor",
            now = Instant.parse("2026-07-26T12:00:00Z")
        )

        val savedUser = repository.save(user)
        val retrievedUser = repository.findByEmail(user.email)

        assertNotNull(retrievedUser)
        assertEquals(savedUser, retrievedUser)
    }

    @Test
    fun `returns null when no user exists with the email`() {
        val result = repository.findByEmail("missing@example.com")

        assertNull(result)
    }

    @Test
    fun `finds user by id`() {
        val now = Instant.parse("2026-07-26T12:00:00Z")

        val user = User.create(
            id = UUID.randomUUID(),
            email = "alex@example.com",
            displayName = "Alex",
            firstName = "Alex",
            lastName = "Taylor",
            now = now
        )

        repository.save(user)

        val found = repository.findById(user.id)

        assertEquals(user, found)
    }
}
