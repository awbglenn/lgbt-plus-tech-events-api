package com.lgbtplustech.events.user.application.usecase

import com.lgbtplustech.events.user.application.command.CreateUserCommand
import com.lgbtplustech.events.user.application.port.inbound.CreateUser
import com.lgbtplustech.events.user.application.port.outbound.UserRepository
import com.lgbtplustech.events.user.domain.User
import org.springframework.stereotype.Service
import java.time.Clock
import java.util.UUID

@Service
class CreateUserUseCase(
    private val repository: UserRepository,
    private val clock: Clock
) : CreateUser {

    override fun execute(command: CreateUserCommand): User {
        val user = User.create(
            id = UUID.randomUUID(),
            email = command.email,
            displayName = command.displayName,
            firstName = command.firstName,
            lastName = command.lastName,
            now = clock.instant()
        )

        return repository.save(user)
    }
}
