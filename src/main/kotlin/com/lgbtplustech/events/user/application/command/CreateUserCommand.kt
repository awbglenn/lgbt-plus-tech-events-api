package com.lgbtplustech.events.user.application.command

data class CreateUserCommand(
    val email: String,
    val displayName: String,
    val firstName: String,
    val lastName: String
)
