package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.exception.EventNotFoundException
import com.lgbtplustech.events.event.application.port.EventRepository
import com.lgbtplustech.events.event.application.port.PublishEvent
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PublishEventUseCase(
    private val eventRepository: EventRepository
) : PublishEvent {

    override fun execute(id: UUID) {
        val event = eventRepository.findById(id)
            ?: throw EventNotFoundException(id)

        event.publish()

        eventRepository.save(event)
    }
}