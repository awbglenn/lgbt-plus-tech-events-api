package com.lgbtplustech.events.event.infrastructure.web.controller

import com.lgbtplustech.events.event.application.pagination.PageRequest
import com.lgbtplustech.events.event.application.pagination.PageResult
import com.lgbtplustech.events.event.application.port.inbound.GetEvent
import com.lgbtplustech.events.event.application.port.inbound.GetEvents
import com.lgbtplustech.events.event.domain.EventStatus
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(EventQueryController::class)
@AutoConfigureMockMvc(addFilters = false)
class EventQueryControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockitoBean
    lateinit var getEvent: GetEvent

    @MockitoBean
    lateinit var getEvents: GetEvents

    @Test
    fun `should return an event`() {
        val event = testEvent()
        whenever(getEvent.execute(event.id))
            .thenReturn(event)

        mockMvc.get("/events/${event.id}")
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value(event.id.toString()) }
                jsonPath("$.title") { value(event.title) }
            }
    }

    @Test
    fun `should return 404 when event does not exist`() {
        whenever(getEvent.execute(any()))
            .thenReturn(null)

        mockMvc.get("/events/${UUID.randomUUID()}")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun `returns paginated events`() {
        val event1 = testEvent(title = "Event 1")
        val event2 = testEvent(title = "Event 2")

        whenever(
            getEvents.execute(
                status = null,
                pageRequest = PageRequest(page = 1, size = 2)
            )
        ).thenReturn(
            PageResult(
                items = listOf(event1, event2),
                page = 1,
                size = 2,
                totalElements = 5,
                totalPages = 3
            )
        )

        mockMvc.perform(
            get("/events")
                .param("page", "1")
                .param("size", "2")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.items.length()").value(2))
            .andExpect(jsonPath("$.items[0].title").value("Event 1"))
            .andExpect(jsonPath("$.items[1].title").value("Event 2"))
            .andExpect(jsonPath("$.page").value(1))
            .andExpect(jsonPath("$.size").value(2))
            .andExpect(jsonPath("$.totalElements").value(5))
            .andExpect(jsonPath("$.totalPages").value(3))

        verify(getEvents).execute(
            status = null,
            pageRequest = PageRequest(page = 1, size = 2)
        )
    }

    @Test
    fun `filters and paginates events`() {
        val event = testEvent(title = "Published event").apply {
            publish()
        }

        whenever(
            getEvents.execute(
                status = EventStatus.PUBLISHED,
                pageRequest = PageRequest(page = 0, size = 10)
            )
        ).thenReturn(
            PageResult(
                items = listOf(event),
                page = 0,
                size = 10,
                totalElements = 1,
                totalPages = 1
            )
        )

        mockMvc.perform(
            get("/events")
                .param("status", "PUBLISHED")
                .param("page", "0")
                .param("size", "10")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.items.length()").value(1))
            .andExpect(jsonPath("$.items[0].status").value("PUBLISHED"))
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(10))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.totalPages").value(1))
    }
}
