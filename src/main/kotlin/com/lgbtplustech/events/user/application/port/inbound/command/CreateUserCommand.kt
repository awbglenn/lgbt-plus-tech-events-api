package com.lgbtplustech.events.user.application.port.inbound.command

data class CreateUserCommand(
    val email: String,
    val displayName: String,
    val firstName: String,
    val lastName: String
)
