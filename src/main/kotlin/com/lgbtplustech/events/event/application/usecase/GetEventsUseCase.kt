package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.port.EventRepository
import com.lgbtplustech.events.event.application.port.GetEvents
import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
import org.springframework.stereotype.Service

@Service
class GetEventsUseCase(
    private val eventRepository: EventRepository
) : GetEvents {

    override fun execute(status: EventStatus?): List<Event> =
        eventRepository.findAll(status)
}