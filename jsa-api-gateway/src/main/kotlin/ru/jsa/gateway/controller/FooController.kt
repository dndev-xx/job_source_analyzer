package ru.jsa.gateway.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FooController {

    private val log = LoggerFactory.getLogger(FooController::class.java)

    @GetMapping("/user/{userId}")
    fun userName(@PathVariable("userId") userId: String?): String {
        log.info("Got a request")
        return "foo"
    }
}