rootProject.name = "job_source_analyzer"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.spring") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

include("jsa-jackson-mapper")
include("jsa-context-common")
include("jsa-config-server")
include("jsa-core-build")
include("jsa-service-registry")
include("jsa-api-gateway")
include("docker-builder")
