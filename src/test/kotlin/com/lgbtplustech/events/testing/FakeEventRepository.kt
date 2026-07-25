package com.lgbtplustech.events.testing

import com.lgbtplustech.events.event.application.pagination.PageRequest
import com.lgbtplustech.events.event.application.pagination.PageResult
import com.lgbtplustech.events.event.application.port.outbound.EventRepository
import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
import java.util.UUID

class FakeEventRepository : EventRepository {
    private val events = mutableMapOf<UUID, Event>()

    lateinit var savedEvent: Event
        private set

    override fun save(event: Event): Event {
        events[event.id] = event
        savedEvent = event
        return event
    }

    override fun findById(id: UUID): Event? =
        events[id]

    override fun findAll(
        status: EventStatus?,
        pageRequest: PageRequest
    ): PageResult<Event> {
        val filteredEvents = events.values
            .filter { status == null || it.status == status }
            .sortedBy { it.startsAt }

        val fromIndex = pageRequest.page * pageRequest.size
        val items = filteredEvents
            .drop(fromIndex)
            .take(pageRequest.size)

        val totalElements = filteredEvents.size
        val totalPages =
            if (totalElements == 0) 0
            else (totalElements + pageRequest.size - 1) / pageRequest.size

        return PageResult(
            items = items,
            page = pageRequest.page,
            size = pageRequest.size,
            totalElements = totalElements,
            totalPages = totalPages
        )
    }
}