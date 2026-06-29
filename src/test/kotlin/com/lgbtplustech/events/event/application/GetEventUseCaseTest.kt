package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.application.port.GetEvent
import com.lgbtplustech.events.event.application.usecase.GetEventUseCase
import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertEquals
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

        assertEquals(event.id, found?.id)
        assertEquals(event.title, found?.title)
    }

    @Test
    fun `returns null when event does not exist`() {
        val repository = FakeEventRepository()
        val getEvent: GetEvent = GetEventUseCase(repository)

        val found = getEvent.execute(UUID.randomUUID())

        assertNull(found)
    }
}