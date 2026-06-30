package com.lgbtplustech.events.event.application.port

import java.util.UUID

interface CancelEvent {
    fun execute(id: UUID)
}