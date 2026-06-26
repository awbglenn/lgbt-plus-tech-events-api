package com.lgbtplustech.events.event.infrastructure.web

import com.lgbtplustech.events.event.application.CreateEvent
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.util.UUID

@WebMvcTest(EventController::class)
@AutoConfigureMockMvc(addFilters = false)
class EventControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockitoBean
    lateinit var createEvent: CreateEvent

    @Test
    fun `controller should create event properly from request`() {
        val eventId = UUID.fromString("42bf9e3c-3445-4d64-8088-3a92694a7817")

        whenever(createEvent.execute(any()))
            .thenReturn(eventId)

        mockMvc.post("/events") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "title": "LGBT+Tech Barcelona",
                  "description": "Monthly meetup",
                  "startsAt": "2026-07-01T18:30:00Z",
                  "endsAt": "2026-07-01T21:00:00Z",
                  "venueName": "Example Venue",
                  "venueAddress": "Barcelona",
                  "capacity": 50
                }
            """.trimIndent()
        }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(eventId.toString()) }
            }
    }
}