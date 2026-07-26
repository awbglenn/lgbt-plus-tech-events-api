package com.lgbtplustech.events.testing

import com.lgbtplustech.events.user.application.port.outbound.UserRepository
import com.lgbtplustech.events.user.domain.User

class FakeUserRepository : UserRepository {

    private val users = mutableListOf<User>()

    override fun save(user: User): User {
        users.add(user)
        return user
    }

    override fun findByEmail(email: String): User? =
        users.find { it.email == email }
}
