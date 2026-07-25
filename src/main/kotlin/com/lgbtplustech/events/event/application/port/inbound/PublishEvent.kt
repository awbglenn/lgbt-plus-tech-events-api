package com.lgbtplustech.events.event.application.port.inbound

import java.util.UUID

interface PublishEvent {
    fun execute(id: UUID)
}
