package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.port.outbound.EventRepository
import com.lgbtplustech.events.event.application.port.inbound.GetEvent
import com.lgbtplustech.events.event.domain.Event
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetEventUseCase(
    private val eventRepository: EventRepository
) : GetEvent {
    override fun execute(id: UUID): Event? =
        eventRepository.findById(id)
}