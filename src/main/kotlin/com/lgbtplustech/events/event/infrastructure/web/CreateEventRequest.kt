package com.lgbtplustech.events.event.infrastructure.web

import java.time.Instant

data class CreateEventRequest(
    val title: String,
    val description: String,
    val startsAt: Instant,
    val endsAt: Instant,
    val venueName: String,
    val venueAddress: String,
    val capacity: Int
)