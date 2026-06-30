package com.lgbtplustech.events.event.infrastructure.web.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.time.Instant

data class CreateEventRequest(
    @field:NotBlank
    val title: String,
    val description: String,
    val startsAt: Instant,
    val endsAt: Instant,
    val venueName: String,
    val venueAddress: String,
    @field:Positive
    val capacity: Int
)