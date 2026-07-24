package com.lgbtplustech.events.event.application.port

import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus

interface GetEvents {
    fun execute(status: EventStatus? = null): List<Event>
}