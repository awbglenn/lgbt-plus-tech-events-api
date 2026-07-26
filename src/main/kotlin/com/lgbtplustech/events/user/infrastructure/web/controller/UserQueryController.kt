package com.lgbtplustech.events.user.infrastructure.web.controller

import com.lgbtplustech.events.user.application.port.inbound.GetUser
import com.lgbtplustech.events.user.infrastructure.web.dto.response.UserResponse
import com.lgbtplustech.events.user.infrastructure.web.dto.response.toResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserQueryController(
    private val getUser: GetUser
) {

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: UUID
    ): UserResponse =
        getUser.execute(id).toResponse()
}
