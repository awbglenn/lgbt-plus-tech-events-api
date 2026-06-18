package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.application.EventRepository
import com.lgbtplustech.events.event.domain.Event
import org.springframework.stereotype.Component

@Component
class PostgresEventRepository(
    private val repository: SpringDataEventRepository
) : EventRepository {

    override fun save(event: Event): Event {
        return repository
            .save(EventEntity.fromDomain(event))
            .toDomain()
    }
}