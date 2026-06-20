package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetEventUseCaseTest {

    @Test
    fun `returns event when found`() {
        val repository = FakeEventRepository()
        val event = testEvent()
        repository.save(event)

        val getEvent: GetEvent = GetEventUseCase(repository)
        val found = getEvent.execute(event.id)

        assertEquals(event.id, found?.id)
    }
}