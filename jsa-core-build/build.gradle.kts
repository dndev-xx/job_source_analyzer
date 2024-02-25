plugins {
    kotlin("jvm")
}

dependencies {
    val okhttpVersion: String by project
    val mockitoVersionCore: String by project
    val mockitoVersionKtl: String by project
    val jupiterVersion: String by project
    val jsoupVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation(project(":jsa-jackson-mapper"))
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")
    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:$mockitoVersionCore")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersionKtl")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
}

