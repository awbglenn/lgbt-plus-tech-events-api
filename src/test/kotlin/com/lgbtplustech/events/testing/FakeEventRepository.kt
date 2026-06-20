package com.lgbtplustech.events.testing

import com.lgbtplustech.events.event.application.EventRepository
import com.lgbtplustech.events.event.domain.Event
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
}