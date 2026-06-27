package com.lgbtplustech.events.event.application

import com.lgbtplustech.events.event.domain.Event
import org.springframework.stereotype.Service

@Service
class GetEventsUseCase(
    private val eventRepository: EventRepository
) : GetEvents {

    override fun execute(): List<Event> =
        eventRepository.findAll()
}