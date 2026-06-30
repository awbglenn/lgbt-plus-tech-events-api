package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.command.UpdateEventCommand
import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.UpdateEvent
import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import java.util.UUID

class UpdateEventUseCaseTest {

    private val clock = Clock.fixed(
        Instant.parse("2026-06-02T10:00:00Z"),
        ZoneOffset.UTC
    )

    @Test
    fun `updates event fields`() {
        val repository = FakeEventRepository()
        val event = testEvent()
        repository.save(event)
        val updateEvent: UpdateEvent = UpdateEventUseCase(repository, clock)

        updateEvent.execute(
            UpdateEventCommand(
                id = event.id,
                title = "Updated title",
                capacity = 100
            )
        )

        val updatedEvent = repository.findById(event.id)

        assertEquals("Updated title", updatedEvent?.title)
        assertEquals(100, updatedEvent?.capacity)
        assertEquals(event.description, updatedEvent?.description)
        assertEquals(Instant.parse("2026-06-02T10:00:00Z"), updatedEvent?.updatedAt)
    }

    @Test
    fun `throws when event does not exist`() {
        val repository = FakeEventRepository()
        val updateEvent: UpdateEvent = UpdateEventUseCase(repository, clock)

        assertThrows<EventNotFoundException> {
            updateEvent.execute(
                UpdateEventCommand(
                    id = UUID.randomUUID(),
                    title = "Updated title"
                )
            )
        }
    }
}