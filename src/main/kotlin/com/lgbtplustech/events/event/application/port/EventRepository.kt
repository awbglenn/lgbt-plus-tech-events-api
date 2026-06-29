package com.lgbtplustech.events.event.application.port

import com.lgbtplustech.events.event.domain.Event
import java.util.UUID

interface EventRepository {
    fun save(event: Event): Event
    fun findById(id: UUID): Event?
    fun findAll(): List<Event>
}