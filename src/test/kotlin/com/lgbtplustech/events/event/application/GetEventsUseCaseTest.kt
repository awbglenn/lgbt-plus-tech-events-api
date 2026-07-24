package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.application.port.GetEvents
import com.lgbtplustech.events.event.application.usecase.GetEventsUseCase
import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.assertEventsEqual
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class GetEventsUseCaseTest {

    @Test
    fun `returns all events when no status is provided`() {
        val repository = FakeEventRepository()
        val useCase: GetEvents = GetEventsUseCase(repository)
        val draftEvent = repository.save(testEvent())
        val publishedEvent = repository.save(
            testEvent(title = "Kotlin Meetup").apply {
                publish()
            }
        )

        val events = useCase.execute()

        assertEventsEqual(
            listOf(draftEvent, publishedEvent),
            events
        )
    }

    @ParameterizedTest(name = "returns only {0} events")
    @EnumSource(EventStatus::class)
    fun `returns events filtered by status`(status: EventStatus) {
        val repository = FakeEventRepository()
        val useCase: GetEvents = GetEventsUseCase(repository)
        repository.save(testEvent(title = "Draft"))
        repository.save(
            testEvent(title = "Published").apply {
                publish()
            }
        )
        repository.save(
            testEvent(title = "Cancelled").apply {
                publish()
                cancel()
            }
        )
        repository.save(
            testEvent(title = "Completed").apply {
                publish()
                complete()
            }
        )

        val events = useCase.execute(status)

        assertEquals(1, events.size)
        assertEquals(status, events.single().status)
    }
}