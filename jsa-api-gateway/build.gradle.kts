plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

extra["springCloudVersion"] = "2023.0.0"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

val kafkaVersion: String = "1.3.23"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("io.projectreactor.kafka:reactor-kafka:$kafkaVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}