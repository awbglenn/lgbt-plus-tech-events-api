package com.lgbtplustech.events.event.application

import java.util.UUID

interface PublishEvent {
    fun execute(id: UUID)
}