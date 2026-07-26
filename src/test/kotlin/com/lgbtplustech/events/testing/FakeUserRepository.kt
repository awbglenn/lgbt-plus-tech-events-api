package com.lgbtplustech.events.testing

import com.lgbtplustech.events.user.application.port.outbound.UserRepository
import com.lgbtplustech.events.user.domain.User
import java.util.UUID

class FakeUserRepository : UserRepository {

    private val users = mutableMapOf<UUID, User>()

    override fun save(user: User): User {
        users.put(user.id, user)
        return user
    }

    override fun findByEmail(email: String): User? =
        users.values.find { it.email == email }

    override fun findById(id: UUID): User? = users[id]
}
