package com.lgbtplustech.events.user.infrastructure.persistence

import com.lgbtplustech.events.user.application.port.outbound.UserRepository
import com.lgbtplustech.events.user.domain.User
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class PostgresUserRepository(
    private val repository: SpringDataUserRepository
) : UserRepository {

    override fun save(user: User): User =
        repository
            .save(UserEntity.fromDomain(user))
            .toDomain()

    override fun findByEmail(email: String): User? =
        repository
            .findByEmail(email)
            ?.toDomain()

    override fun findById(id: UUID): User? =
        repository.findById(id)
            .map(UserEntity::toDomain)
            .orElse(null)
}
