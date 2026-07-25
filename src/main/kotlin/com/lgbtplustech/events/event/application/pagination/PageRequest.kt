package com.lgbtplustech.events.event.application.pagination

data class PageRequest(
    val page: Int = 0,
    val size: Int = 20
) {
    init {
        require(page >= 0) { "Page cannot be negative." }
        require(size > 0) { "Page size must be greater than zero." }
    }
}
