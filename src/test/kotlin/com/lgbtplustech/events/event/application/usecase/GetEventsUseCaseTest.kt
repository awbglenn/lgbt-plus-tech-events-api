package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.pagination.PageRequest
import com.lgbtplustech.events.event.application.port.inbound.GetEvents
import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.FakeEventRepository
import com.lgbtplustech.events.testing.assertEventsEqual
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.time.Instant
import java.time.temporal.ChronoUnit

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

        val result = useCase.execute(pageRequest = PageRequest())

        assertEventsEqual(
            listOf(draftEvent, publishedEvent),
            result.items
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

        val result = useCase.execute(status, PageRequest())

        assertEquals(1, result.items.size)
        assertEquals(status, result.items.single().status)
    }

    @Test
    fun `returns first page of events`() {
        val repository = FakeEventRepository()

        val base = Instant.parse("2026-08-01T18:00:00Z")

        repeat(5) { index ->
            repository.save(
                testEvent(
                    title = "Event $index",
                    startsAt = base.plus(index.toLong(), ChronoUnit.DAYS),
                    endsAt = base.plus(index.toLong(), ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS)
                )
            )
        }

        val useCase = GetEventsUseCase(repository)

        val result = useCase.execute(
            status = null,
            pageRequest = PageRequest(page = 0, size = 2)
        )

        assertEquals(2, result.items.size)
        assertEquals(0, result.page)
        assertEquals(2, result.size)
        assertEquals(5, result.totalElements)
        assertEquals(3, result.totalPages)
    }

    @Test
    fun `returns second page of events`() {
        val repository = FakeEventRepository()

        val base = Instant.parse("2026-08-01T18:00:00Z")

        repeat(5) { index ->
            repository.save(
                testEvent(
                    title = "Event $index",
                    startsAt = base.plus(index.toLong(), ChronoUnit.DAYS),
                    endsAt = base.plus(index.toLong(), ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS)
                )
            )
        }

        val useCase = GetEventsUseCase(repository)

        val result = useCase.execute(
            status = null,
            pageRequest = PageRequest(page = 1, size = 2)
        )

        assertEquals(
            listOf("Event 2", "Event 3"),
            result.items.map { it.title }
        )
    }
}