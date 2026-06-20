package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.domain.Event
import java.util.UUID

interface GetEvent {
    fun execute(id: UUID): Event?
}