plugins {
    kotlin("jvm")
    id("checkstyle")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

subprojects {
    group = "ru.jsa"
    version = "1.0.0"
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    tasks.withType<Checkstyle> {
        configFile = project.file("configuration/checkstyle/checkstyle.xml")
    }
}




