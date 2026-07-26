package com.lgbtplustech.events.user.infrastructure.web.controller

import com.lgbtplustech.events.user.application.port.inbound.CreateUser
import com.lgbtplustech.events.user.domain.User
import com.lgbtplustech.events.user.domain.UserRole
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.Instant
import java.util.UUID

@WebMvcTest(UserCommandController::class)
@AutoConfigureMockMvc(addFilters = false)
class UserCommandControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockitoBean
    lateinit var createUser: CreateUser

    @Test
    fun `should create user properly from request`() {
        val userId = UUID.randomUUID()
        val now = Instant.parse("2026-07-26T12:00:00Z")

        whenever(createUser.execute(any()))
            .thenReturn(
                User(
                    id = userId,
                    email = "alex@example.com",
                    displayName = "Alex",
                    firstName = "Alex",
                    lastName = "Taylor",
                    role = UserRole.MEMBER,
                    createdAt = now,
                    updatedAt = now
                )
            )

        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "email": "alex@example.com",
                  "displayName": "Alex",
                  "firstName": "Alex",
                  "lastName": "Taylor"
                }
            """.trimIndent()
        }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(userId.toString()) }
            }
    }

    @Test
    fun `should return bad request when email is invalid`() {
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "email": "not-an-email",
                  "displayName": "Alex",
                  "firstName": "Alex",
                  "lastName": "Taylor"
                }
            """.trimIndent()
        }
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun `should return bad request when display name is blank`() {
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "email": "alex@example.com",
                  "displayName": "",
                  "firstName": "Alex",
                  "lastName": "Taylor"
                }
            """.trimIndent()
        }
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun `should return bad request when first name is blank`() {
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "email": "alex@example.com",
                  "displayName": "Alex",
                  "firstName": "",
                  "lastName": "Taylor"
                }
            """.trimIndent()
        }
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun `should return bad request when last name is blank`() {
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "email": "alex@example.com",
                  "displayName": "Alex",
                  "firstName": "Alex",
                  "lastName": ""
                }
            """.trimIndent()
        }
            .andExpect {
                status { isBadRequest() }
            }
    }
}
