package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.command.UpdateEventCommand
import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.EventRepository
import com.lgbtplustech.events.event.application.port.UpdateEvent
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Instant

@Service
class UpdateEventUseCase(
    private val eventRepository: EventRepository,
    private val clock: Clock
) : UpdateEvent {

    override fun execute(command: UpdateEventCommand) {
        val event = eventRepository.findById(command.id)
            ?: throw EventNotFoundException(command.id)

        event.updateDetails(
            title = command.title ?: event.title,
            description = command.description ?: event.description,
            startsAt = command.startsAt ?: event.startsAt,
            endsAt = command.endsAt ?: event.endsAt,
            venueName = command.venueName ?: event.venueName,
            venueAddress = command.venueAddress ?: event.venueAddress,
            capacity = command.capacity ?: event.capacity,
            updatedAt = Instant.now(clock)
        )

        eventRepository.save(event)
    }
}