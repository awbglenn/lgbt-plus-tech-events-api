package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.assertEventsEqual
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import java.util.*

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostgresEventRepository::class)
class PostgresEventRepositoryTest(
    @Autowired private val repository: PostgresEventRepository
) {

    companion object {
        @Container
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:17")
    }

    @Test
    fun `saves and returns event`() {
        val event = testEvent()

        val savedEvent = repository.save(event)

        assertEventsEqual(event, savedEvent)
    }

    @Test
    fun `finds event by id`() {
        val event = repository.save(testEvent())

        val foundEvent = repository.findById(event.id)

        assertEventsEqual(event, foundEvent)
    }

    @Test
    fun `returns null when event does not exist`() {
        val foundEvent = repository.findById(UUID.randomUUID())

        assertNull(foundEvent)
    }

    @Test
    fun `finds all events when status is not provided`() {
        val draftEvent = repository.save(
            testEvent(
                title = "Draft event",
            )
        )

        val publishedEvent = repository.save(
            testEvent(
                title = "Published event",
            ).apply { publish() }
        )

        val events = repository.findAll(null)

        assertEventsEqual(
            setOf(draftEvent, publishedEvent),
            events.toSet()
        )
    }

    @ParameterizedTest(name = "finds only events with status {0}")
    @EnumSource(EventStatus::class)
    fun `finds only events corresponding to a status`(status: EventStatus) {
        repository.save(testEvent(
            title = "Draft event"
        ))
        repository.save(testEvent(
            title = "Published event"
        ).apply {
            publish()
        })
        repository.save(testEvent(
            title = "Cancelled event"
        ).apply {
            publish()
            cancel()
        })
        repository.save(testEvent(
            title = "Completed event"
        ).apply {
            publish()
            complete()
        })

        val foundEvents = repository.findAll(status)

        assertEquals(1, foundEvents.size)
        assertEquals(status, foundEvents.single().status)
    }
}