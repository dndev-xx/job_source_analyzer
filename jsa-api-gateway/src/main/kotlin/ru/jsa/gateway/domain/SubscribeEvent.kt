package ru.jsa.gateway.domain

import ru.jsa.gateway.enums.StatusSubscribe
import java.util.UUID

data class SubscribeEvent(
    val requestId: UUID,
    var status: StatusSubscribe = StatusSubscribe.INIT
)
