package com.lgbtplustech.events.event.domain

import java.time.Instant
import java.util.UUID

class Event(
    val id: UUID,
    var title: String,
    var description: String,
    var startsAt: Instant,
    var endsAt: Instant,
    var venueName: String,
    var venueAddress: String,
    var capacity: Int,
    val createdAt: Instant,
    var updatedAt: Instant,
    status: EventStatus = EventStatus.DRAFT
) {
    var status: EventStatus = status
        private set

    init {
        require(title.isNotBlank()) { "Title cannot be blank" }
        require(description.isNotBlank() || status != EventStatus.PUBLISHED) { "Description cannot be blank for a published event" }
        require(venueName.isNotBlank() || status != EventStatus.PUBLISHED) { "Venue name cannot be blank for a published event" }
        require(venueAddress.isNotBlank() || status != EventStatus.PUBLISHED) { "Venue address cannot be blank for a published event" }
        require(capacity > 0) { "Capacity must be greater than zero" }
        require(endsAt > startsAt) { "Event must end after it starts" }
        require(updatedAt >= createdAt) { "Updated at cannot be before created at" }
    }

    fun publish() {
        check(status == EventStatus.DRAFT) {
            "Only draft events can be published"
        }
        check(description.isNotBlank()) {
            "Description is required to publish an event"
        }
        check(venueName.isNotBlank()) {
            "Venue name is required to publish an event"
        }
        check(venueAddress.isNotBlank()) {
            "Venue address is required to publish an event"
        }
        status = EventStatus.PUBLISHED
    }

    fun updateDetails(
        title: String,
        description: String,
        startsAt: Instant,
        endsAt: Instant,
        venueName: String,
        venueAddress: String,
        capacity: Int,
        updatedAt: Instant
    ) {
        check(status != EventStatus.CANCELLED) {
            "A cancelled event cannot be updated"
        }

        check(status != EventStatus.COMPLETED) {
            "A completed event cannot be updated"
        }

        if (status == EventStatus.PUBLISHED) {
            require(description.isNotBlank()) {
                "Description cannot be blank for a published event"
            }
            require(venueName.isNotBlank()) {
                "Venue name cannot be blank for a published event"
            }
            require(venueAddress.isNotBlank()) {
                "Venue address cannot be blank for a published event"
            }
        }

        require(title.isNotBlank()) { "Title cannot be blank" }
        require(capacity > 0) { "Capacity must be greater than zero" }
        require(endsAt > startsAt) { "Event must end after it starts" }
        require(updatedAt >= createdAt) { "Updated at cannot be before created at" }

        this.title = title
        this.description = description
        this.startsAt = startsAt
        this.endsAt = endsAt
        this.venueName = venueName
        this.venueAddress = venueAddress
        this.capacity = capacity
        this.updatedAt = updatedAt
    }

    fun cancel() {
        check(status != EventStatus.CANCELLED) {
            "Event is already cancelled"
        }

        check(status != EventStatus.COMPLETED) {
            "Completed events cannot be cancelled"
        }

        status = EventStatus.CANCELLED
    }
}