package com.lgbtplustech.events.event.application.port

import com.lgbtplustech.events.event.application.command.CreateEventCommand
import java.util.UUID

interface CreateEvent {
    fun execute(command: CreateEventCommand): UUID
}