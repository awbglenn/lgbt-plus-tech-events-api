package com.lgbtplustech.events.event.application.port

import com.lgbtplustech.events.event.application.command.UpdateEventCommand

interface UpdateEvent {
    fun execute(command: UpdateEventCommand)
}