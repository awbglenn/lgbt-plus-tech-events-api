package com.lgbtplustech.events.event.infrastructure.web.dto

import java.time.Instant

data class UpdateEventRequest(
    val title: String? = null,
    val description: String? = null,
    val startsAt: Instant? = null,
    val endsAt: Instant? = null,
    val venueName: String? = null,
    val venueAddress: String? = null,
    val capacity: Int? = null
)