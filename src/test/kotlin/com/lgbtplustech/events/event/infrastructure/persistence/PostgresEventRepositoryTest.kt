package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer

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
    fun `saves event`() {
        val event = testEvent()

        val savedEvent = repository.save(event)

        assertEquals(event.id, savedEvent.id)
        assertEquals("LGBT+Tech Barcelona", savedEvent.title)
        assertEquals(EventStatus.DRAFT, savedEvent.status)
        assertEquals(event.createdAt, savedEvent.createdAt)
        assertEquals(event.updatedAt, savedEvent.updatedAt)
    }

    @Test
    fun `finds saved event`() {
        val event = testEvent()
        repository.save(event)

        val found = repository.findById(event.id)

        assertEquals(event.id, found?.id)
        assertEquals(event.title, found?.title)
        assertEquals(event.description, found?.description)
    }
}