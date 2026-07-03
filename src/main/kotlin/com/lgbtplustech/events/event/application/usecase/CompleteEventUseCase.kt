package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.exception.EventCannotBeCompletedException
import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.CompleteEvent
import com.lgbtplustech.events.event.application.port.EventRepository
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Instant
import java.util.UUID

@Service
class CompleteEventUseCase(
    private val eventRepository: EventRepository,
    private val clock: Clock
) : CompleteEvent {

    override fun execute(id: UUID) {
        val event = eventRepository.findById(id)
            ?: throw EventNotFoundException(id)

        if (event.endsAt > Instant.now(clock)) {
            throw EventCannotBeCompletedException(
                "An event cannot be completed before it has ended"
            )
        }

        event.complete()

        eventRepository.save(event)
    }
}