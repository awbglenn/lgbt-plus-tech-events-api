package com.lgbtplustech.events.event.application.port

import java.util.UUID

interface PublishEvent {
    fun execute(id: UUID)
}