package com.lgbtplustech.events.user.application.usecase

import com.lgbtplustech.events.user.application.exception.UserNotFoundException
import com.lgbtplustech.events.user.application.port.inbound.GetUser
import com.lgbtplustech.events.user.application.port.outbound.UserRepository
import com.lgbtplustech.events.user.domain.User
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetUserUseCase(
    private val repository: UserRepository
) : GetUser {

    override fun execute(id: UUID): User =
        repository.findById(id)
            ?: throw UserNotFoundException(id)
}