package ru.jsa.gateway.controller

import brave.Tracer
import com.fasterxml.jackson.databind.ObjectMapper
import jsa.mapper.api.v1.models.ResponseResult
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import ru.jsa.gateway.dto.SubscribeDataRequest
import ru.jsa.gateway.service.SubscriptionService

@RestController
@RequestMapping("/api/v1/jsa")
data class SubscribeChanelController(
    private val tracer: Tracer,
    private val subscriptionService: SubscriptionService,
    private val objectMapper: ObjectMapper,
) {

    private val log = LoggerFactory.getLogger(SubscribeChanelController::class.java)

    @PostMapping("/subscribe")
    fun startProcessSubscribe(
        @RequestHeader("trace_id") traceId: String,
        @RequestHeader("span_id") spanId: String,
        @RequestHeader("parent_span_id") parentSpanId: String,
        @RequestBody request: SubscribeDataRequest,
    ): Mono<ResponseResult> {
        return Mono.just(request)
            .doOnNext { println(request) }
            .flatMap { subscriptionService.sendEventToBuilder(traceId, spanId, request) }
            .then(Mono.fromCallable { ResponseResult.SUCCESS })
            .onErrorResume { error ->
                log.error("Error occurred while sending event to builder: ${error.message}")
                Mono.error(
                    ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error occurred while processing request"
                    )
                )
            }
    }
}