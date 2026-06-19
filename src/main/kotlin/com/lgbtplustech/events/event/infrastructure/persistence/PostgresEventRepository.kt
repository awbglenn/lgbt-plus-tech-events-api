package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.application.EventRepository
import com.lgbtplustech.events.event.domain.Event
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PostgresEventRepository(
    private val repository: SpringDataEventRepository
) : EventRepository {

    override fun save(event: Event): Event {
        return repository
            .save(EventEntity.fromDomain(event))
            .toDomain()
    }

    override fun findById(id: UUID): Event? =
        repository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
}