package com.lgbtplustech.events.event.domain

import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Instant
import java.util.stream.Stream

class EventTest {

    @Test
    fun `creates event as draft`() {
        val event = testEvent()

        assertEquals(EventStatus.DRAFT, event.status)
    }

    @Test
    fun `publishes a draft event`() {
        val event = testEvent()

        event.publish()

        assertEquals(EventStatus.PUBLISHED, event.status)
    }

    @Test
    fun `does not create event with blank title`() {
        assertThrows<IllegalArgumentException> {
            testEvent(title = "")
        }
    }

    @Test
    fun `does not create event with invalid capacity`() {
        assertThrows<IllegalArgumentException> {
            testEvent(capacity = 0)
        }
    }

    @Test
    fun `does not create event that ends before it starts`() {
        assertThrows<IllegalArgumentException> {
            testEvent(
                startsAt = Instant.parse("2026-07-01T21:00:00Z"),
                endsAt = Instant.parse("2026-07-01T18:30:00Z")
            )
        }
    }

    @Test
    fun `does not create event updated before it was created`() {
        assertThrows<IllegalArgumentException> {
            testEvent(
                createdAt = Instant.parse("2026-06-01T10:00:00Z"),
                updatedAt = Instant.parse("2026-06-01T09:00:00Z")
            )
        }
    }

    @Test
    fun `should publish draft event`() {
        val event = testEvent()

        event.publish()

        assertEquals(EventStatus.PUBLISHED, event.status)
    }

    @ParameterizedTest(name = "cannot publish event without {0}")
    @MethodSource("incompletePublishableEvents")
    fun `cannot publish incomplete event`(
        field: String,
        event: Event
    ) {
        assertThrows<IllegalStateException> {
            event.publish()
        }
    }

    companion object {
        @JvmStatic
        fun incompletePublishableEvents(): Stream<Arguments> = Stream.of(
            Arguments.of("description", testEvent(description = "")),
            Arguments.of("venue name", testEvent(venueName = "")),
            Arguments.of("venue address", testEvent(venueAddress = ""))
        )
    }

}