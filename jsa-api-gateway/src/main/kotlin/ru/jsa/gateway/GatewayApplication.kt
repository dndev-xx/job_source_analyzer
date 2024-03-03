package ru.jsa.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.context.config.annotation.RefreshScope

@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
class GatewayApplication

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}