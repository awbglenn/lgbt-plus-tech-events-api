package com.lgbtplustech.events.event.application.port.inbound.command

import java.time.Instant

data class CreateEventCommand(
    val title: String,
    val description: String,
    val startsAt: Instant,
    val endsAt: Instant,
    val venueName: String,
    val venueAddress: String,
    val capacity: Int
)