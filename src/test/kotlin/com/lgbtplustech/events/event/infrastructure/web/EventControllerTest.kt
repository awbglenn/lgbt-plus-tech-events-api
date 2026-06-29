package com.lgbtplustech.events.event.infrastructure.web

import com.lgbtplustech.events.event.application.port.CreateEvent
import com.lgbtplustech.events.event.application.port.GetEvent
import com.lgbtplustech.events.event.application.port.GetEvents
import com.lgbtplustech.events.testing.testEvent
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.UUID

@WebMvcTest(EventController::class)
@AutoConfigureMockMvc(addFilters = false)
class EventControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockitoBean
    lateinit var createEvent: CreateEvent

    @MockitoBean
    lateinit var getEvent: GetEvent

    @MockitoBean
    lateinit var getEvents: GetEvents

    @Test
    fun `should create event properly from request`() {
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

    @Test
    fun `should return bad request when title is blank`() {
        mockMvc.post("/events") {
            contentType = MediaType.APPLICATION_JSON
            content = """
            {
              "title": "",
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
                status { isBadRequest() }
            }
    }

    @Test
    fun `should return bad request when capacity is not positive`() {
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
              "capacity": 0
            }
        """.trimIndent()
        }
            .andExpect {
                status { isBadRequest() }
            }
    }

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