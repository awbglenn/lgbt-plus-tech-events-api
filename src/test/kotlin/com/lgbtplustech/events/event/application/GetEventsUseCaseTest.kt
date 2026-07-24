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
}