package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.port.EventRepository
import com.lgbtplustech.events.event.application.port.GetEvents
import com.lgbtplustech.events.event.domain.Event
import org.springframework.stereotype.Service

@Service
class GetEventsUseCase(
    private val eventRepository: EventRepository
) : GetEvents {

    override fun execute(): List<Event> =
        eventRepository.findAll()
}