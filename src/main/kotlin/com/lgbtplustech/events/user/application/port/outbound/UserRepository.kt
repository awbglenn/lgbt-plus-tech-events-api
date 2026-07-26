package com.lgbtplustech.events.user.application.port.outbound

import com.lgbtplustech.events.user.domain.User

interface UserRepository {
    fun save(user: User): User
    fun findByEmail(email: String): User?
}
