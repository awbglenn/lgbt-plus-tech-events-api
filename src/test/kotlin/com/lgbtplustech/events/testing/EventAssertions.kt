package com.lgbtplustech.events.testing

import com.lgbtplustech.events.event.domain.Event
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull

fun assertEventsEqual(expected: Event, actual: Event?) {
    assertNotNull(actual)

    actual!!

    assertEquals(expected.id, actual.id)
    assertEquals(expected.title, actual.title)
    assertEquals(expected.description, actual.description)
    assertEquals(expected.startsAt, actual.startsAt)
    assertEquals(expected.endsAt, actual.endsAt)
    assertEquals(expected.venueName, actual.venueName)
    assertEquals(expected.venueAddress, actual.venueAddress)
    assertEquals(expected.capacity, actual.capacity)
    assertEquals(expected.status, actual.status)
    assertEquals(expected.createdAt, actual.createdAt)
    assertEquals(expected.updatedAt, actual.updatedAt)
}

fun assertEventsEqual(
    expected: Collection<Event>,
    actual: Collection<Event>
) {
    assertEquals(expected.size, actual.size)

    expected
        .zip(actual)
        .forEach { (expectedEvent, actualEvent) ->
            assertEventsEqual(expectedEvent, actualEvent)
        }
}

fun assertEventsEqualIgnoringOrder(
    expected: Collection<Event>,
    actual: Collection<Event>
) {
    assertEquals(expected.size, actual.size)

    val actualById = actual.associateBy(Event::id)

    expected.forEach { expectedEvent ->
        assertEventsEqual(
            expectedEvent,
            actualById[expectedEvent.id]
        )
    }
}
