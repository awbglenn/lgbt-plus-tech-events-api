package com.lgbtplustech.events.event.application

import java.util.UUID

class EventNotFoundException(id: UUID) :
    RuntimeException("Event $id not found")