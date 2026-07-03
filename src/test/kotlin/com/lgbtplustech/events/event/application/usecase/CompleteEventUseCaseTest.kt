package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.exception.EventCannotBeCompletedException
import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.CompleteEvent
import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import java.util.UUID

class CompleteEventUseCaseTest {

    private val clock = Clock.fixed(
        Instant.parse("2026-07-02T10:00:00Z"),
        ZoneOffset.UTC
    )

    @Test
    fun `completes event that has ended`() {
        val repository = FakeEventRepository()
        val event = testEvent(
            startsAt = Instant.parse("2026-07-01T18:30:00Z"),
            endsAt = Instant.parse("2026-07-01T21:00:00Z")
        )
        event.publish()
        repository.save(event)

        val completeEvent: CompleteEvent =
            CompleteEventUseCase(repository, clock)

        completeEvent.execute(event.id)

        val completedEvent = repository.findById(event.id)

        assertEquals(EventStatus.COMPLETED, completedEvent?.status)
    }

    @Test
    fun `throws when event has not ended`() {
        val repository = FakeEventRepository()
        val event = testEvent(
            startsAt = Instant.parse("2026-07-03T18:30:00Z"),
            endsAt = Instant.parse("2026-07-03T21:00:00Z")
        )
        event.publish()
        repository.save(event)

        val completeEvent: CompleteEvent =
            CompleteEventUseCase(repository, clock)

        assertThrows<EventCannotBeCompletedException> {
            completeEvent.execute(event.id)
        }
    }

    @Test
    fun `throws when event does not exist`() {
        val repository = FakeEventRepository()

        val completeEvent: CompleteEvent =
            CompleteEventUseCase(repository, clock)

        assertThrows<EventNotFoundException> {
            completeEvent.execute(UUID.randomUUID())
        }
    }
}