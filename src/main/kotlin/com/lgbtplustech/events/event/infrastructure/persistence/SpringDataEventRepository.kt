package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.domain.EventStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SpringDataEventRepository : JpaRepository<EventEntity, UUID> {

    fun findAllByOrderByStartsAtAsc(pageable: Pageable): Page<EventEntity>

    fun findAllByStatusOrderByStartsAtAsc(
        status: EventStatus,
        pageable: Pageable,
    ): Page<EventEntity>
}
