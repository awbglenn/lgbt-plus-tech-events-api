package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.command.CreateEventCommand
import com.lgbtplustech.events.event.application.port.inbound.CreateEvent
import com.lgbtplustech.events.event.application.port.outbound.EventRepository
import com.lgbtplustech.events.event.domain.Event
import org.springframework.stereotype.Service
import java.time.Clock
import java.util.UUID

@Service
class CreateEventUseCase(
    private val eventRepository: EventRepository,
    private val clock: Clock
) : CreateEvent {
    override fun execute(command: CreateEventCommand): UUID {
        val event = Event.create(
            id = UUID.randomUUID(),
            title = command.title,
            description = command.description,
            startsAt = command.startsAt,
            endsAt = command.endsAt,
            venueName = command.venueName,
            venueAddress = command.venueAddress,
            capacity = command.capacity,
            now = clock.instant()
        )

        return eventRepository.save(event).id
    }
}