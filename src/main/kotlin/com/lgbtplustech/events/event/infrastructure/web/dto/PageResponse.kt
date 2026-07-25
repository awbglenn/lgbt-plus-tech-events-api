package com.lgbtplustech.events.event.infrastructure.web.dto

data class PageResponse<T>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)
