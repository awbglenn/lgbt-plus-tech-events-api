package com.lgbtplustech.events.user.domain

enum class UserRole(
    private val level: Int
) {
    MEMBER(1),
    ORGANISER(2),
    ADMIN(3);

    fun hasAtLeast(requiredRole: UserRole): Boolean =
        level >= requiredRole.level
}
