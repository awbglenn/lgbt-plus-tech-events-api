package com.lgbtplustech.events.user.infrastructure.web.controller

import com.lgbtplustech.events.user.application.exception.UserNotFoundException
import com.lgbtplustech.events.user.application.port.inbound.GetUser
import com.lgbtplustech.events.user.domain.User
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.Instant
import java.util.UUID

@WebMvcTest(UserQueryController::class)
@AutoConfigureMockMvc(addFilters = false)
class UserQueryControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockitoBean
    lateinit var getUser: GetUser

    @Test
    fun `returns user by id`() {
        val userId =
            UUID.fromString("42bf9e3c-3445-4d64-8088-3a92694a7817")
        val now = Instant.parse("2026-07-26T12:00:00Z")

        val user = User.create(
            id = userId,
            email = "alex@example.com",
            displayName = "Alex",
            firstName = "Alex",
            lastName = "Taylor",
            now = now
        )

        whenever(getUser.execute(userId))
            .thenReturn(user)

        mockMvc.get("/users/$userId")
            .andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$.id") { value(userId.toString()) }
                jsonPath("$.email") { value("alex@example.com") }
                jsonPath("$.displayName") { value("Alex") }
                jsonPath("$.firstName") { value("Alex") }
                jsonPath("$.lastName") { value("Taylor") }
                jsonPath("$.role") { value("MEMBER") }
                jsonPath("$.createdAt") { value(now.toString()) }
                jsonPath("$.updatedAt") { value(now.toString()) }
            }
    }

    @Test
    fun `returns problem details when user does not exist`() {
        val userId =
            UUID.fromString("42bf9e3c-3445-4d64-8088-3a92694a7817")

        whenever(getUser.execute(userId))
            .thenThrow(UserNotFoundException(userId))

        mockMvc.get("/users/$userId")
            .andExpect {
                status { isNotFound() }
                content { contentType("application/problem+json") }
                jsonPath("$.title") { value("User not found") }
                jsonPath("$.status") { value(404) }
                jsonPath("$.detail") {
                    value("User $userId was not found")
                }
            }
    }
}
