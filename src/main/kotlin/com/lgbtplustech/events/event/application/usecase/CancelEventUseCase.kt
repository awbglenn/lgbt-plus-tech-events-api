package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.CancelEvent
import com.lgbtplustech.events.event.application.port.EventRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CancelEventUseCase(
    private val eventRepository: EventRepository
) : CancelEvent {

    override fun execute(id: UUID) {
        val event = eventRepository.findById(id)
            ?: throw EventNotFoundException(id)

        event.cancel()

        eventRepository.save(event)
    }
}