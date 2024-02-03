plugins {
    kotlin("jvm") version "1.9.22"
    id("checkstyle")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
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




