package com.lgbtplustech.events.user.application.usecase

import com.lgbtplustech.events.testing.FakeUserRepository
import com.lgbtplustech.events.user.application.exception.UserNotFoundException
import com.lgbtplustech.events.user.domain.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.UUID

class GetUserUseCaseTest {

    private val repository = FakeUserRepository()
    private val useCase = GetUserUseCase(repository)

    @Test
    fun `returns user when found`() {
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

        val found = useCase.execute(user.id)

        assertEquals(user, found)
    }

    @Test
    fun `throws when user does not exist`() {
        val id = UUID.randomUUID()

        assertThrows<UserNotFoundException> {
            useCase.execute(id)
        }
    }
}
