package com.lgbtplustech.events.user.infrastructure.persistence

import com.lgbtplustech.events.user.application.port.outbound.UserRepository
import com.lgbtplustech.events.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class PostgresUserRepository(
    private val springDataUserRepository: SpringDataUserRepository
) : UserRepository {

    override fun save(user: User): User =
        springDataUserRepository
            .save(UserEntity.fromDomain(user))
            .toDomain()

    override fun findByEmail(email: String): User? =
        springDataUserRepository
            .findByEmail(email)
            ?.toDomain()
}
