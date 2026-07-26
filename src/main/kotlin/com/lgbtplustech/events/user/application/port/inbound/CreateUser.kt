package com.lgbtplustech.events.user.application.port.inbound

import com.lgbtplustech.events.user.application.port.inbound.command.CreateUserCommand
import com.lgbtplustech.events.user.domain.User

interface CreateUser {
    fun execute(command: CreateUserCommand): User
}
