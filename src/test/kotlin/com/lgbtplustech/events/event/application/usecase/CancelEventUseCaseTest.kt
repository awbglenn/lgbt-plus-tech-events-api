package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.CancelEvent
import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class CancelEventUseCaseTest {

    @Test
    fun `cancels event`() {
        val repository = FakeEventRepository()
        val event = testEvent()

        repository.save(event)

        val cancelEvent: CancelEvent =
            CancelEventUseCase(repository)

        cancelEvent.execute(event.id)

        val cancelledEvent = repository.findById(event.id)

        assertEquals(
            EventStatus.CANCELLED,
            cancelledEvent?.status
        )
    }

    @Test
    fun `throws when event does not exist`() {
        val repository = FakeEventRepository()

        val cancelEvent: CancelEvent =
            CancelEventUseCase(repository)

        assertThrows<EventNotFoundException> {
            cancelEvent.execute(UUID.randomUUID())
        }
    }
}