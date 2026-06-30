package com.lgbtplustech.events.event.domain

import com.lgbtplustech.events.event.application.exception.EventCannotBePublishedException
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
        require(description.isNotBlank() || status != EventStatus.PUBLISHED) { "Description cannot be blank for a published event" }
        require(venueName.isNotBlank() || status != EventStatus.PUBLISHED) { "Venue name cannot be blank for a published event" }
        require(venueAddress.isNotBlank() || status != EventStatus.PUBLISHED) { "Venue address cannot be blank for a published event" }
        require(capacity > 0) { "Capacity must be greater than zero" }
        require(endsAt > startsAt) { "Event must end after it starts" }
        require(updatedAt >= createdAt) { "Updated at cannot be before created at" }
    }

    fun publish() {
        check(status == EventStatus.DRAFT) {
            throw EventCannotBePublishedException(
                "Only draft events can be published"
            )
        }
        check(description.isNotBlank()) {
            throw EventCannotBePublishedException(
                "Description is required to publish an event"
            )
        }
        check(venueName.isNotBlank()) {
            throw EventCannotBePublishedException(
                "Venue name is required to publish an event"
            )
        }
        check(venueAddress.isNotBlank()) {
            throw EventCannotBePublishedException(
                "Venue address is required to publish an event"            )
        }
        status = EventStatus.PUBLISHED
    }
}