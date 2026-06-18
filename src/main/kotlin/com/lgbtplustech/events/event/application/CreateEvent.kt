package com.lgbtplustech.events.event.application

import java.util.UUID

interface CreateEvent {
    fun execute(command: CreateEventCommand): UUID
}