package com.lgbtplustech.events.event.application.port

import com.lgbtplustech.events.event.domain.Event
import java.util.UUID

interface GetEvent {
    fun execute(id: UUID): Event?
}