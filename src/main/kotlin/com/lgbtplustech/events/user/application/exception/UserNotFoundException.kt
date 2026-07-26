package com.lgbtplustech.events.user.application.exception

import java.util.UUID

class UserNotFoundException(
    id: UUID
) : RuntimeException("User $id was not found")
