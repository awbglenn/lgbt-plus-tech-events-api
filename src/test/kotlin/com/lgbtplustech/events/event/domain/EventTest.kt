package com.lgbtplustech.events.event.domain

import com.lgbtplustech.events.event.application.exception.EventCannotBePublishedException
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

    @ParameterizedTest(name = "cannot publish event without {0}")
    @MethodSource("incompletePublishableEvents")
    fun `cannot publish incomplete event`(
        field: String,
        event: Event
    ) {
        assertThrows<EventCannotBePublishedException> {
            event.publish()
        }
    }

    @Test
    fun `updates draft event`() {
        val event = testEvent()

        event.updateDetails(
            title = "Updated title",
            description = "Updated description",
            startsAt = Instant.parse("2026-08-01T18:30:00Z"),
            endsAt = Instant.parse("2026-08-01T21:00:00Z"),
            venueName = "Updated Venue",
            venueAddress = "Updated Address",
            capacity = 100,
            updatedAt = Instant.parse("2026-06-02T10:00:00Z")
        )

        assertEquals("Updated title", event.title)
        assertEquals(100, event.capacity)
        assertEquals(EventStatus.DRAFT, event.status)
    }

    @ParameterizedTest(name = "cannot update event when {0}")
    @MethodSource("invalidEventUpdates")
    fun `cannot update event with invalid details`(
        scenario: String,
        update: Event.() -> Unit
    ) {
        val event = testEvent()

        assertThrows<IllegalArgumentException> {
            event.update()
        }
    }

    @Test
    fun `updates published event details`() {
        val event = testEvent()
        event.publish()

        event.updateDetails(
            title = "Updated published event",
            description = "Updated description",
            startsAt = event.startsAt,
            endsAt = event.endsAt,
            venueName = event.venueName,
            venueAddress = event.venueAddress,
            capacity = event.capacity,
            updatedAt = Instant.parse("2026-06-02T10:00:00Z")
        )

        assertEquals("Updated published event", event.title)
    }

    @ParameterizedTest(name = "cannot update published event when {0}")
    @MethodSource("incompletePublishedEventUpdates")
    fun `cannot update published event with incomplete details`(
        scenario: String,
        update: Event.() -> Unit
    ) {
        val event = testEvent()
        event.publish()

        assertThrows<IllegalArgumentException> {
            event.update()
        }
    }

    companion object {
        @JvmStatic
        fun incompletePublishableEvents(): Stream<Arguments> = Stream.of(
            Arguments.of("description", testEvent(description = "")),
            Arguments.of("venue name", testEvent(venueName = "")),
            Arguments.of("venue address", testEvent(venueAddress = ""))
        )

        @JvmStatic
        fun invalidEventUpdates(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "title is blank",
                { event: Event ->
                    event.updateDetails(
                        title = "",
                        description = event.description,
                        startsAt = event.startsAt,
                        endsAt = event.endsAt,
                        venueName = event.venueName,
                        venueAddress = event.venueAddress,
                        capacity = event.capacity,
                        updatedAt = Instant.parse("2026-06-02T10:00:00Z")
                    )
                }
            ),
            Arguments.of(
                "capacity is not positive",
                { event: Event ->
                    event.updateDetails(
                        title = event.title,
                        description = event.description,
                        startsAt = event.startsAt,
                        endsAt = event.endsAt,
                        venueName = event.venueName,
                        venueAddress = event.venueAddress,
                        capacity = 0,
                        updatedAt = Instant.parse("2026-06-02T10:00:00Z")
                    )
                }
            ),
            Arguments.of(
                "end date is before start date",
                { event: Event ->
                    event.updateDetails(
                        title = event.title,
                        description = event.description,
                        startsAt = Instant.parse("2026-08-01T21:00:00Z"),
                        endsAt = Instant.parse("2026-08-01T18:30:00Z"),
                        venueName = event.venueName,
                        venueAddress = event.venueAddress,
                        capacity = event.capacity,
                        updatedAt = Instant.parse("2026-06-02T10:00:00Z")
                    )
                }
            )
        )

        @JvmStatic
        fun incompletePublishedEventUpdates(): Stream<Arguments> =
            Stream.of(
                Arguments.of("description is blank", { event: Event ->
                    event.updateDetails(
                        description = "",
                        title = event.title,
                        startsAt = event.startsAt,
                        endsAt = event.endsAt,
                        venueName = event.venueName,
                        venueAddress = event.venueAddress,
                        capacity = event.capacity,
                        updatedAt = Instant.parse("2026-06-02T10:00:00Z")
                    )
                }),
                Arguments.of("venue name is blank", { event: Event ->
                    event.updateDetails(
                        venueName = "",
                        title = event.title,
                        description = event.description,
                        startsAt = event.startsAt,
                        endsAt = event.endsAt,
                        venueAddress = event.venueAddress,
                        capacity = event.capacity,
                        updatedAt = Instant.parse("2026-06-02T10:00:00Z")
                    )
                }),
                Arguments.of("venue address is blank", { event: Event ->
                    event.updateDetails(
                        venueAddress = "",
                        title = event.title,
                        description = event.description,
                        venueName = event.venueName,
                        startsAt = event.startsAt,
                        endsAt = event.endsAt,
                        capacity = event.capacity,
                        updatedAt = Instant.parse("2026-06-02T10:00:00Z")
                    )
                }),
            )

    }
}