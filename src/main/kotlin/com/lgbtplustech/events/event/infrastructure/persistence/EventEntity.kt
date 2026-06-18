package com.lgbtplustech.events.event.infrastructure.persistence

import com.lgbtplustech.events.event.domain.Event
import com.lgbtplustech.events.event.domain.EventStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "events")
class EventEntity(
    @Id
    val id: UUID,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val description: String,

    @Column(name = "starts_at", nullable = false)
    val startsAt: Instant,

    @Column(name = "ends_at", nullable = false)
    val endsAt: Instant,

    @Column(name = "venue_name", nullable = false)
    val venueName: String,

    @Column(name = "venue_address", nullable = false)
    val venueAddress: String,

    @Column(nullable = false)
    val capacity: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: EventStatus,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant
) {
    fun toDomain() = Event(
        id = id,
        title = title,
        description = description,
        startsAt = startsAt,
        endsAt = endsAt,
        venueName = venueName,
        venueAddress = venueAddress,
        capacity = capacity,
        createdAt = createdAt,
        updatedAt = updatedAt,
        status = status
    )

    companion object {
        fun fromDomain(event: Event) = EventEntity(
            id = event.id,
            title = event.title,
            description = event.description,
            startsAt = event.startsAt,
            endsAt = event.endsAt,
            venueName = event.venueName,
            venueAddress = event.venueAddress,
            capacity = event.capacity,
            status = event.status,
            createdAt = event.createdAt,
            updatedAt = event.updatedAt
        )
    }
}