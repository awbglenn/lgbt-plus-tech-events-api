package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.testing.assertEventsEqual
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
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
                id = UUID.randomUUID(),
                title = "Draft event",
            )
        )
        val publishedEvent = testEvent(
            id = UUID.randomUUID(),
            title = "Published event",
        )
        publishedEvent.publish()

        val publishedEventSaved = repository.save(
            publishedEvent
        )

        val events = repository.findAll(null)

        assertEventsEqual(
            setOf(draftEvent, publishedEventSaved),
            events.toSet()
        )
    }
}