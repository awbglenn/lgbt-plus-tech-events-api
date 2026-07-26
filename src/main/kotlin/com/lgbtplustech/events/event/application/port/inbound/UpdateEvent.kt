package com.lgbtplustech.events.event.application.port.inbound

import com.lgbtplustech.events.event.application.port.inbound.command.UpdateEventCommand

interface UpdateEvent {
    fun execute(command: UpdateEventCommand)
}
