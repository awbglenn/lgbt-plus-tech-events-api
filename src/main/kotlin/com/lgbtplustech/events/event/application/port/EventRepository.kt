package com.lgbtplustech.events.event.application.port

import com.lgbtplustech.events.event.application.pagination.PageRequest
import com.lgbtplustech.events.event.application.pagination.PageResult
import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
import java.util.UUID

interface EventRepository {
    fun save(event: Event): Event
    fun findById(id: UUID): Event?
    fun findAll(
        status: EventStatus? = null,
        pageRequest: PageRequest = PageRequest(),
    ): PageResult<Event>
}
