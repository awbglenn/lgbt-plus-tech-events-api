package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.domain.Event
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateEventUseCase(
    private val eventRepository: EventRepository
) : CreateEvent {
    override fun execute(command: CreateEventCommand): UUID {
        val event = Event(
            id = UUID.randomUUID(),
            title = command.title,
            description = command.description,
            startsAt = command.startsAt,
            endsAt = command.endsAt,
            venueName = command.venueName,
            venueAddress = command.venueAddress,
            capacity = command.capacity
        )

        return eventRepository.save(event).id
    }
}