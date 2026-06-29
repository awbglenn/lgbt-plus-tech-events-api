package com.lgbtplustech.events.event.application.port

import com.lgbtplustech.events.event.domain.Event

interface GetEvents {
    fun execute(): List<Event>
}