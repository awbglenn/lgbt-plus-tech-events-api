package com.lgbtplustech.events.event.application.port

import com.lgbtplustech.events.event.application.pagination.PageRequest
import com.lgbtplustech.events.event.application.pagination.PageResult
import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus

interface GetEvents {
    fun execute(
        status: EventStatus? = null,
        pageRequest: PageRequest = PageRequest()
    ): PageResult<Event>
}
