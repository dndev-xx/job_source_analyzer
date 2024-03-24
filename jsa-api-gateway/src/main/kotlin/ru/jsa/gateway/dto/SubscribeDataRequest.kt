package ru.jsa.gateway.dto

import jsa.mapper.api.v1.models.CdvRequest
import ru.jsa.gateway.enums.Subscription
import java.util.UUID

data class SubscribeDataRequest(
    val subscriptions: List<Subscription>?,
    override val requestType: String = "subscribe to chanel",
    override val requestId: String = UUID.randomUUID().toString(),
    val tgLink: String?,
) : CdvRequest