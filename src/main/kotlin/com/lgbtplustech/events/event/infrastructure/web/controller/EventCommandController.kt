package com.lgbtplustech.events.event.infrastructure.web.controller

import com.lgbtplustech.events.event.application.port.CreateEvent
import com.lgbtplustech.events.event.application.command.CreateEventCommand
import com.lgbtplustech.events.event.application.command.UpdateEventCommand
import com.lgbtplustech.events.event.application.port.CancelEvent
import com.lgbtplustech.events.event.application.port.PublishEvent
import com.lgbtplustech.events.event.application.port.UpdateEvent
import com.lgbtplustech.events.event.infrastructure.web.dto.*
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/events")
class EventCommandController(
    private val createEvent: CreateEvent,
    private val publishEvent: PublishEvent,
    private val updateEvent: UpdateEvent,
    private val cancelEvent: CancelEvent
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateEventRequest): CreateEventResponse {
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

    //TODO add authentication here, only admins/organisers can publish events
    @PatchMapping("/{id}/publish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun publish(@PathVariable id: UUID) {
        publishEvent.execute(id)
    }

    //TODO add authentication here, only admins/organisers can edit events
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(
        @PathVariable id: UUID,
        @RequestBody request: UpdateEventRequest
    ) {
        updateEvent.execute(
            UpdateEventCommand(
                id = id,
                title = request.title,
                description = request.description,
                startsAt = request.startsAt,
                endsAt = request.endsAt,
                venueName = request.venueName,
                venueAddress = request.venueAddress,
                capacity = request.capacity
            )
        )
    }

    //TODO add authentication here, only admins/organisers can cancel events
    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancel(@PathVariable id: UUID) {
        cancelEvent.execute(id)
    }
}