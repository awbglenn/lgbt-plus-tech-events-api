package com.lgbtplustech.events.event.infrastructure.web

import com.lgbtplustech.events.event.application.CreateEvent
import com.lgbtplustech.events.event.application.CreateEventCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventController(
    private val createEvent: CreateEvent
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateEventRequest): CreateEventResponse {
        val id = createEvent.execute(
            CreateEventCommand(
                title = request.title,
                description = request.description,
                startsAt = request.startsAt,
                endsAt = request.endsAt,
                venueName = request.venueName,
                venueAddress = request.venueAddress,
                capacity = request.capacity
            )
        )

        return CreateEventResponse(id)
    }
}