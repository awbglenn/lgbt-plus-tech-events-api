package com.lgbtplustech.events.user.application.usecase

import com.lgbtplustech.events.testing.FakeUserRepository
import com.lgbtplustech.events.user.application.command.CreateUserCommand
import com.lgbtplustech.events.user.domain.UserRole
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class CreateUserUseCaseTest {

    private val now = Instant.parse("2026-07-26T12:00:00Z")
    private val clock = Clock.fixed(now, ZoneOffset.UTC)
    private val repository = FakeUserRepository()
    private val useCase = CreateUserUseCase(repository, clock)

    @Test
    fun `creates and saves a new member user`() {
        val command = CreateUserCommand(
            email = "alex@example.com",
            displayName = "Alex",
            firstName = "Alex",
            lastName = "Taylor"
        )

        val user = useCase.execute(command)

        assertEquals(command.email, user.email)
        assertEquals(command.displayName, user.displayName)
        assertEquals(command.firstName, user.firstName)
        assertEquals(command.lastName, user.lastName)
        assertEquals(UserRole.MEMBER, user.role)
        assertEquals(now, user.createdAt)
        assertEquals(now, user.updatedAt)
        assertEquals(user, repository.findByEmail(command.email))
    }
}
