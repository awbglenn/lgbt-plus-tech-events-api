package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.exception.EventCannotBePublishedException
import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.inbound.PublishEvent
import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.UUID
import java.util.stream.Stream

class PublishEventUseCaseTest {

    @Test
    fun `should publish existing event`() {
        val repository = FakeEventRepository()
        val event = testEvent()
        repository.save(event)
        val useCase: PublishEvent = PublishEventUseCase(repository)

        useCase.execute(event.id)

        assertEquals(
            EventStatus.PUBLISHED,
            repository.findById(event.id)?.status
        )
    }

    @Test
    fun `throws when event does not exist`() {
        val repository = FakeEventRepository()
        val useCase: PublishEvent = PublishEventUseCase(repository)

        assertThrows<EventNotFoundException> {
            useCase.execute(UUID.randomUUID())
        }
    }

    @ParameterizedTest(name = "cannot publish event when {0}")
    @MethodSource("invalidEvents")
    fun `throws when event cannot be published`(
        reason: String,
        event: Event
    ) {
        val repository = FakeEventRepository()
        repository.save(event)
        val useCase: PublishEvent = PublishEventUseCase(repository)

        assertThrows<EventCannotBePublishedException> {
            useCase.execute(event.id)
        }
    }

    companion object {

        @JvmStatic
        fun invalidEvents() = Stream.of(
            Arguments.of(
                "description is missing",
                testEvent(description = "")
            ),
            Arguments.of(
                "venue name is missing",
                testEvent(venueName = "")
            ),
            Arguments.of(
                "venue address is missing",
                testEvent(venueAddress = "")
            )
        )
    }

}