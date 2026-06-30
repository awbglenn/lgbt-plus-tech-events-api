package com.lgbtplustech.events.event.infrastructure.web.controller

import com.lgbtplustech.events.event.application.port.GetEvent
import com.lgbtplustech.events.event.application.port.GetEvents
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
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
    fun `should return all events`() {
        val first = testEvent()
        val second = testEvent(title = "Kotlin Meetup")

        whenever(getEvents.execute())
            .thenReturn(listOf(first, second))

        mockMvc.get("/events")
            .andExpect {
                status { isOk() }
                jsonPath("$.length()") { value(2) }
                jsonPath("$[0].title") { value(first.title) }
                jsonPath("$[1].title") { value(second.title) }
            }
    }
}