package com.lgbtplustech.events.event.application.command

import java.time.Instant
import java.util.UUID

data class UpdateEventCommand(
    val id: UUID,
    val title: String? = null,
    val description: String? = null,
    val startsAt: Instant? = null,
    val endsAt: Instant? = null,
    val venueName: String? = null,
    val venueAddress: String? = null,
    val capacity: Int? = null
)