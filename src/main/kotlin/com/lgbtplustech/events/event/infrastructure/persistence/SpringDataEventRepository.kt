package com.lgbtplustech.events.event.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SpringDataEventRepository : JpaRepository<EventEntity, UUID>