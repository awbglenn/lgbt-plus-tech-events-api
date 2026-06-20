package com.lgbtplustech.events.testing

import com.lgbtplustech.events.event.domain.Event
import java.time.Instant
import java.util.UUID

fun testEvent(
    id: UUID = UUID.randomUUID(),
    title: String = "LGBT+Tech Barcelona",
    description: String = "Monthly meetup",
    startsAt: Instant = Instant.parse("2026-07-01T18:30:00Z"),
    endsAt: Instant = Instant.parse("2026-07-01T21:00:00Z"),
    venueName: String = "Example Venue",
    venueAddress: String = "Barcelona",
    capacity: Int = 50,
    createdAt: Instant = Instant.parse("2026-06-01T10:00:00Z"),
    updatedAt: Instant = Instant.parse("2026-06-01T10:00:00Z")
): Event =
    Event(
        id = id,
        title = title,
        description = description,
        startsAt = startsAt,
        endsAt = endsAt,
        venueName = venueName,
        venueAddress = venueAddress,
        capacity = capacity,
        createdAt = createdAt,
        updatedAt = updatedAt
    )