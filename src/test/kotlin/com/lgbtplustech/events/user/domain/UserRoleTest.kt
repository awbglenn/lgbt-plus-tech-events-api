package com.lgbtplustech.events.user.domain

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UserRoleTest {

    @Test
    fun `member has member privileges`() {
        assertTrue(UserRole.MEMBER.hasAtLeast(UserRole.MEMBER))
    }

    @Test
    fun `member does not have organiser privileges`() {
        assertFalse(UserRole.MEMBER.hasAtLeast(UserRole.ORGANISER))
    }

    @Test
    fun `organiser has member privileges`() {
        assertTrue(UserRole.ORGANISER.hasAtLeast(UserRole.MEMBER))
    }

    @Test
    fun `organiser has organiser privileges`() {
        assertTrue(UserRole.ORGANISER.hasAtLeast(UserRole.ORGANISER))
    }

    @Test
    fun `organiser does not have admin privileges`() {
        assertFalse(UserRole.ORGANISER.hasAtLeast(UserRole.ADMIN))
    }

    @Test
    fun `admin has all privileges`() {
        assertTrue(UserRole.ADMIN.hasAtLeast(UserRole.MEMBER))
        assertTrue(UserRole.ADMIN.hasAtLeast(UserRole.ORGANISER))
        assertTrue(UserRole.ADMIN.hasAtLeast(UserRole.ADMIN))
    }
}
