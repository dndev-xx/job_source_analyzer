package ru.jsa.gateway.config

import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ser.std.StringSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.sender.SenderOptions
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

@Configuration
class KafkaProducerConfig

@Value("\${spring.kafka.bootstrap-servers}")
private lateinit var bootstrapServers: String

fun producerConfig(): SenderOptions<String, Any> {
    val props = mapOf(
        ProducerConfig.ACKS_CONFIG to "all",
        ProducerConfig.RETRIES_CONFIG to 5,
        ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to true,
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
    )
    return SenderOptions.create(props)
}

@Bean
fun reactiveKafkaProducerTemplate(): ReactiveKafkaProducerTemplate<String, Any> {
    return ReactiveKafkaProducerTemplate(producerConfig())
}