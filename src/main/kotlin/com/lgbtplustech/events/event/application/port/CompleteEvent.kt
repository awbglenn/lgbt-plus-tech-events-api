package com.lgbtplustech.events.event.application.port

import java.util.UUID

interface CompleteEvent {
    fun execute(id: UUID)
}
