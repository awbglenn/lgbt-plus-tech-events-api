package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.domain.Event

interface GetEvents {
    fun execute(): List<Event>
}