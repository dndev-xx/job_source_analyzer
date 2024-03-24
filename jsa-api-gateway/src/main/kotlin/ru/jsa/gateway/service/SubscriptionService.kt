package ru.jsa.gateway.service

import brave.Tracer
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.jsa.gateway.controller.SubscribeChanelController
import ru.jsa.gateway.dto.SubscribeDataRequest

@Service
data class SubscriptionService(
    private val tracer: Tracer,
    private val kafkaTemplate: ReactiveKafkaProducerTemplate<String, Any>,
) {

    private val log = LoggerFactory.getLogger(SubscribeChanelController::class.java)

    companion object {
        const val BUILDER = "builder-service"
    }

    fun sendEventToBuilder(traceId: String, spanId: String, message: SubscribeDataRequest): Mono<Void> {
        return Mono.defer {
            val kafkaHeaders = mapOf<String, Any>(
                "trace_id" to traceId,
                "span_id" to spanId
            )

            val kafkaMessage: Message<SubscribeDataRequest> = MessageBuilder
                .withPayload(message)
                .copyHeaders(kafkaHeaders)
                .build()

            kafkaTemplate.send(BUILDER, kafkaMessage)
                .doOnNext { log.info("Message sent successfully: $it") }
                .doOnError { error -> log.error("Failed to send message: ${error.message}") }
                .then()
        }
    }
}