package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.application.port.EventRepository
import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
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

    override fun findAll(status: EventStatus?): List<Event> =
        if (status == null) {
            repository.findAllByOrderByStartsAtAsc()
        } else {
            repository.findAllByStatusOrderByStartsAtAsc(status)
        }.map { it.toDomain() }
}