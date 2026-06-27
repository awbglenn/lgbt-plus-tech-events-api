package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GetEventsUseCaseTest {

    @Test
    fun `should return all events`() {
        val repository = FakeEventRepository()
        val first = testEvent()
        val second = testEvent(title = "Kotlin Meetup")
        repository.save(first)
        repository.save(second)

        val getEvents: GetEvents = GetEventsUseCase(repository)
        val events = getEvents.execute()

        assertEquals(2, events.size)
    }

}