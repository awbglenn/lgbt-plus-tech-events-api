package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.domain.Event
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Instant
import java.util.UUID

@Service
class CreateEventUseCase(
    private val eventRepository: EventRepository,
    private val clock: Clock
) : CreateEvent {
    override fun execute(command: CreateEventCommand): UUID {
        val now = Instant.now(clock)

        val event = Event(
            id = UUID.randomUUID(),
            title = command.title,
            description = command.description,
            startsAt = command.startsAt,
            endsAt = command.endsAt,
            venueName = command.venueName,
            venueAddress = command.venueAddress,
            capacity = command.capacity,
            createdAt = now,
            updatedAt = now
        )

        return eventRepository.save(event).id
    }
}