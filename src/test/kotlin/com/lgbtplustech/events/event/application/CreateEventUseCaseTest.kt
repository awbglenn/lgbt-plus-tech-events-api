package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.FakeEventRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class CreateEventUseCaseTest {

    @Test
    fun `creates draft event`() {
        val repository = FakeEventRepository()
        val clock = Clock.fixed(
            Instant.parse("2026-06-01T10:00:00Z"),
            ZoneOffset.UTC
        )
        val createEvent: CreateEvent = CreateEventUseCase(repository, clock)
        val eventId = createEvent.execute(testCommand())

        val savedEvent = repository.savedEvent

        assertEquals(savedEvent.id, eventId)
        assertEquals(EventStatus.DRAFT, savedEvent.status)
        assertEquals("LGBT+Tech Barcelona", savedEvent.title)
        assertEquals(Instant.parse("2026-06-01T10:00:00Z"), savedEvent.createdAt)
        assertEquals(Instant.parse("2026-06-01T10:00:00Z"), savedEvent.updatedAt)
    }

    private fun testCommand() = CreateEventCommand(
        title = "LGBT+Tech Barcelona",
        description = "Monthly meetup",
        startsAt = Instant.parse("2026-07-01T18:30:00Z"),
        endsAt = Instant.parse("2026-07-01T21:00:00Z"),
        venueName = "Example Venue",
        venueAddress = "Barcelona",
        capacity = 50
    )

}