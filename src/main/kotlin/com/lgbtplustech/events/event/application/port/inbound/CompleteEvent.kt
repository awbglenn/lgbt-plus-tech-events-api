package com.lgbtplustech.events.event.application.port.inbound

import java.util.UUID

interface CompleteEvent {
    fun execute(id: UUID)
}
