package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.exception.EventCannotBePublishedException
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

        try {
            event.publish()
        } catch (e: IllegalStateException) {
            throw EventCannotBePublishedException(e.message ?: "Event cannot be published")
        }

        eventRepository.save(event)
    }
}