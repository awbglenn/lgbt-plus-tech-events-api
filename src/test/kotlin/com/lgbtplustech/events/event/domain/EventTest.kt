package com.lgbtplustech.events.event.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.util.UUID

class EventTest {

    @Test
    fun `creates event as draft`() {
        val event = testEvent()

        assertEquals(EventStatus.DRAFT, event.status)
    }

    @Test
    fun `publishes a draft event`() {
        val event = testEvent()

        event.publish()

        assertEquals(EventStatus.PUBLISHED, event.status)
    }

    @Test
    fun `does not create event with blank title`() {
        assertThrows<IllegalArgumentException> {
            testEvent(title = "")
        }
    }

    @Test
    fun `does not create event with invalid capacity`() {
        assertThrows<IllegalArgumentException> {
            testEvent(capacity = 0)
        }
    }

    @Test
    fun `does not create event that ends before it starts`() {
        assertThrows<IllegalArgumentException> {
            testEvent(
                startsAt = Instant.parse("2026-07-01T21:00:00Z"),
                endsAt = Instant.parse("2026-07-01T18:30:00Z")
            )
        }
    }

    private fun testEvent(
        title: String = "Test", capacity: Int = 50,
        startsAt: Instant = Instant.parse("2026-07-01T18:30:00Z"),
        endsAt: Instant = Instant.parse("2026-07-01T21:00:00Z")
    ) = Event(
        id = UUID.randomUUID(),
        title = title,
        description = "Monthly meetup",
        startsAt = startsAt,
        endsAt = endsAt,
        venueName = "Example Venue",
        venueAddress = "Barcelona",
        capacity = capacity
    )
}