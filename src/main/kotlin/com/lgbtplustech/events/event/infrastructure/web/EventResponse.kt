package com.lgbtplustech.events.event.infrastructure.web

import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
import java.time.Instant
import java.util.UUID

data class EventResponse(
    val id: UUID,
    val title: String,
    val description: String,
    val startsAt: Instant,
    val endsAt: Instant,
    val venueName: String,
    val venueAddress: String,
    val capacity: Int,
    val status: EventStatus
)

fun Event.toResponse() = EventResponse(
    id = id,
    title = title,
    description = description,
    startsAt = startsAt,
    endsAt = endsAt,
    venueName = venueName,
    venueAddress = venueAddress,
    capacity = capacity,
    status = status
)