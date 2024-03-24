package ru.jsa.gateway.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopicConfig {

    @Bean
    fun builderTopic(): NewTopic {
        return TopicBuilder.name("builder-service").build()
    }
}