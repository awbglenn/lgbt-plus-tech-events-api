package com.lgbtplustech.events.event.infrastructure.web.controller

import com.lgbtplustech.events.event.application.command.UpdateEventCommand
import com.lgbtplustech.events.event.application.exception.EventCannotBeCompletedException
import com.lgbtplustech.events.event.application.exception.EventCannotBePublishedException
import com.lgbtplustech.events.event.application.port.inbound.CancelEvent
import com.lgbtplustech.events.event.application.port.inbound.CompleteEvent
import com.lgbtplustech.events.event.application.port.inbound.CreateEvent
import com.lgbtplustech.events.event.application.port.inbound.PublishEvent
import com.lgbtplustech.events.event.application.port.inbound.UpdateEvent
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.util.*
import java.util.stream.Stream

@WebMvcTest(EventCommandController::class)
@AutoConfigureMockMvc(addFilters = false)
class EventCommandControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockitoBean
    lateinit var createEvent: CreateEvent

    @MockitoBean
    lateinit var publishEvent: PublishEvent

    @MockitoBean
    lateinit var updateEvent: UpdateEvent

    @MockitoBean
    lateinit var cancelEvent: CancelEvent

    @MockitoBean
    lateinit var completeEvent: CompleteEvent

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
    fun `should publish event`() {
        val eventId = UUID.randomUUID()

        mockMvc.patch("/events/$eventId/publish")
            .andExpect {
                status { isNoContent() }
            }

        verify(publishEvent).execute(eventId)
    }

    @Test
    fun `should cancel event`() {
        val eventId = UUID.randomUUID()

        mockMvc.patch("/events/$eventId/cancel")
            .andExpect {
                status { isNoContent() }
            }

        verify(cancelEvent).execute(eventId)
    }

    @Test
    fun `updates event`() {
        val eventId = UUID.randomUUID()

        mockMvc.patch("/events/$eventId") {
            contentType = MediaType.APPLICATION_JSON
            content = """
            {
                "title": "Updated title",
                "capacity": 100
            }
        """.trimIndent()
        }
            .andExpect {
                status { isNoContent() }
            }

        verify(updateEvent).execute(
            UpdateEventCommand(
                id = eventId,
                title = "Updated title",
                capacity = 100
            )
        )
    }

    @Test
    fun `completes event`() {
        val eventId = UUID.randomUUID()

        mockMvc.patch("/events/$eventId/complete")
            .andExpect {
                status { isNoContent() }
            }

        verify(completeEvent).execute(eventId)
    }

    @Test
    fun `returns problem details when event cannot be completed`() {
        val eventId = UUID.randomUUID()

        whenever(completeEvent.execute(eventId))
            .thenThrow(
                EventCannotBeCompletedException(
                    "An event cannot be completed before it has ended"
                )
            )

        mockMvc.patch("/events/$eventId/complete")
            .andExpect {
                status { isConflict() }
                content { contentType("application/problem+json") }
                jsonPath("$.title") { value("Event cannot be completed") }
                jsonPath("$.status") { value(409) }
                jsonPath("$.detail") {
                    value("An event cannot be completed before it has ended")
                }
            }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("eventCannotBePublishedExceptions")
    fun `returns problem details when event cannot be published`(
        scenario: String,
        exceptionMessage: String
    ) {
        val eventId = UUID.randomUUID()

        whenever(publishEvent.execute(eventId))
            .thenThrow(EventCannotBePublishedException(exceptionMessage))

        mockMvc.patch("/events/$eventId/publish")
            .andExpect {
                status { isConflict() }
                content { contentType("application/problem+json") }
                jsonPath("$.title") { value("Event cannot be published") }
                jsonPath("$.status") { value(409) }
                jsonPath("$.detail") { value(exceptionMessage) }
            }
    }

    companion object {
        @JvmStatic
        fun eventCannotBePublishedExceptions(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "missing description",
                "Description is required to publish an event"
            ),
            Arguments.of(
                "missing venue name",
                "Venue name is required to publish an event"
            ),
            Arguments.of(
                "missing venue address",
                "Venue address is required to publish an event"
            )
        )
    }
}