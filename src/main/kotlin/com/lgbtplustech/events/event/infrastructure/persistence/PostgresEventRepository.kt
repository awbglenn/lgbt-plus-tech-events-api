package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.application.pagination.PageRequest
import com.lgbtplustech.events.event.application.pagination.PageResult
import com.lgbtplustech.events.event.application.port.EventRepository
import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
import org.springframework.data.domain.PageRequest as SpringPageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
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

    override fun findAll(
        status: EventStatus?,
        pageRequest: PageRequest
    ): PageResult<Event> {
        val pageable = SpringPageRequest.of(
            pageRequest.page,
            pageRequest.size,
        )

        val page =
            if (status == null) {
                repository.findAllByOrderByStartsAtAsc(pageable)
            } else {
                repository.findAllByStatusOrderByStartsAtAsc(status, pageable)
            }

        return PageResult(
            items = page.content.map { it.toDomain() },
            page = page.number,
            size = page.size,
            totalElements = page.totalElements.toInt(),
            totalPages = page.totalPages
        )
    }
}