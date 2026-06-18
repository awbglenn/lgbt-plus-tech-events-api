package com.lgbtplustech.events.event.domain

import java.time.Instant
import java.util.UUID

class Event(
    val id: UUID,
    val title: String,
    val description: String,
    val startsAt: Instant,
    val endsAt: Instant,
    val venueName: String,
    val venueAddress: String,
    val capacity: Int,
    val createdAt: Instant,
    val updatedAt: Instant,
    status: EventStatus = EventStatus.DRAFT
) {
    var status: EventStatus = status
        private set

    init {
        require(title.isNotBlank()) { "Title cannot be blank" }
        require(description.isNotBlank()) { "Description cannot be blank" }
        require(venueName.isNotBlank()) { "Venue name cannot be blank" }
        require(venueAddress.isNotBlank()) { "Venue address cannot be blank" }
        require(capacity > 0) { "Capacity must be greater than zero" }
        require(endsAt > startsAt) { "Event must end after it starts" }
        require(updatedAt >= createdAt) { "Updated at cannot be before created at" }
    }

    fun publish() {
        require(status == EventStatus.DRAFT) {
            "Only draft events can be published"
        }

        status = EventStatus.PUBLISHED
    }
}