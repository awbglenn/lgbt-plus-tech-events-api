package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.PublishEvent
import com.lgbtplustech.events.event.application.usecase.PublishEventUseCase
import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class PublishEventUseCaseTest {

    @Test
    fun `should publish existing event`() {
        val repository = FakeEventRepository()
        val event = testEvent()
        repository.save(event)
        val publishEvent: PublishEvent =
            PublishEventUseCase(repository)

        publishEvent.execute(event.id)

        assertEquals(
            EventStatus.PUBLISHED,
            repository.findById(event.id)?.status
        )
    }

    @Test
    fun `throws when event does not exist`() {
        val repository = FakeEventRepository()

        val publishEvent: PublishEvent =
            PublishEventUseCase(repository)

        assertThrows<EventNotFoundException> {
            publishEvent.execute(UUID.randomUUID())
        }
    }

}