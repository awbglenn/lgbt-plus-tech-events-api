package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.domain.Event

interface EventRepository {
    fun save(event: Event): Event
}