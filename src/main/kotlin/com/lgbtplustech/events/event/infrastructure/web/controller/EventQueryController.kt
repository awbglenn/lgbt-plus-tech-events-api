package com.lgbtplustech.events.event.infrastructure.web.controller

import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.GetEvent
import com.lgbtplustech.events.event.application.port.GetEvents
import com.lgbtplustech.events.event.infrastructure.web.dto.EventResponse
import com.lgbtplustech.events.event.infrastructure.web.dto.toResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/events")
class EventQueryController(
    private val getEvent: GetEvent,
    private val getEvents: GetEvents,
) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): EventResponse {
        val event = getEvent.execute(id) ?: throw EventNotFoundException(id)

        return event.toResponse()
    }

    //TODO add pagination e.g. GET /events?page=0&size=20
    @GetMapping
    fun getAll(): List<EventResponse> =
        getEvents.execute()
            .map { it.toResponse() }
}