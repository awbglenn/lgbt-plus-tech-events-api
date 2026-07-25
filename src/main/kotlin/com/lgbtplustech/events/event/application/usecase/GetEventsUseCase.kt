package com.lgbtplustech.events.event.application.usecase

import com.lgbtplustech.events.event.application.pagination.PageRequest
import com.lgbtplustech.events.event.application.pagination.PageResult
import com.lgbtplustech.events.event.application.port.EventRepository
import com.lgbtplustech.events.event.application.port.GetEvents
import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
import org.springframework.stereotype.Service

@Service
class GetEventsUseCase(
    private val repository: EventRepository
) : GetEvents {

    override fun execute(
        status: EventStatus?,
        pageRequest: PageRequest
    ): PageResult<Event> =
        repository.findAll(status, pageRequest)
}
