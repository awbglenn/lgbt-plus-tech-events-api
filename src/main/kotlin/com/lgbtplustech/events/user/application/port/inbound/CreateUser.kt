package com.lgbtplustech.events.user.application.port.inbound

import com.lgbtplustech.events.user.application.command.CreateUserCommand
import com.lgbtplustech.events.user.domain.User

interface CreateUser {
    fun execute(command: CreateUserCommand): User
}
