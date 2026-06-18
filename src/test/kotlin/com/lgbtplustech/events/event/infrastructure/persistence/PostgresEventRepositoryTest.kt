package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import java.time.Instant
import java.util.UUID

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostgresEventRepository::class)
class PostgresEventRepositoryTest(
    @Autowired private val repository: PostgresEventRepository
) {

    @Test
    fun `saves event`() {
        val event = testEvent()

        val savedEvent = repository.save(event)

        assertEquals(event.id, savedEvent.id)
        assertEquals("LGBT+Tech Barcelona", savedEvent.title)
        assertEquals(EventStatus.DRAFT, savedEvent.status)
        assertEquals(event.createdAt, savedEvent.createdAt)
        assertEquals(event.updatedAt, savedEvent.updatedAt)
    }

    private fun testEvent() = Event(
        id = UUID.randomUUID(),
        title = "LGBT+Tech Barcelona",
        description = "Monthly meetup",
        startsAt = Instant.parse("2026-07-01T18:30:00Z"),
        endsAt = Instant.parse("2026-07-01T21:00:00Z"),
        venueName = "Example Venue",
        venueAddress = "Barcelona",
        capacity = 50,
        createdAt = Instant.parse("2026-06-01T10:00:00Z"),
        updatedAt = Instant.parse("2026-06-01T10:00:00Z")
    )
}