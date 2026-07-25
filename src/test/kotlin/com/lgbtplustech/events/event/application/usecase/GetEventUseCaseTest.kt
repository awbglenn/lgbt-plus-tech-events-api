package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.port.inbound.GetEvent
import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.assertEventsEqual
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class GetEventUseCaseTest {

    @Test
    fun `returns event when found`() {
        val repository = FakeEventRepository()
        val event = testEvent()
        repository.save(event)
        val getEvent: GetEvent = GetEventUseCase(repository)

        val found = getEvent.execute(event.id)

        assertEventsEqual(event, found)
    }

    @Test
    fun `returns null when event does not exist`() {
        val repository = FakeEventRepository()
        val getEvent: GetEvent = GetEventUseCase(repository)

        val found = getEvent.execute(UUID.randomUUID())

        assertNull(found)
    }
}