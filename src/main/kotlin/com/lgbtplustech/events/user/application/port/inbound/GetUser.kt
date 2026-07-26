package com.lgbtplustech.events.user.application.port.inbound

import com.lgbtplustech.events.user.domain.User
import java.util.UUID

interface GetUser {
    fun execute(id: UUID): User
}
