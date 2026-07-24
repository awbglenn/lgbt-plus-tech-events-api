package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.domain.EventStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SpringDataEventRepository : JpaRepository<EventEntity, UUID> {
    fun findAllByStatus(status: EventStatus): List<EventEntity>
}
