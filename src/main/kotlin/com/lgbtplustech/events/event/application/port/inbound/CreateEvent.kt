package com.lgbtplustech.events.event.application.port.inbound

import com.lgbtplustech.events.event.application.port.inbound.command.CreateEventCommand
import java.util.UUID

interface CreateEvent {
    fun execute(command: CreateEventCommand): UUID
}
