package com.lgbtplustech.events.event.application.port.inbound

import java.util.UUID

interface CancelEvent {
    fun execute(id: UUID)
}
