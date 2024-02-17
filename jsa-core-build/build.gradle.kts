plugins {
    kotlin("jvm")
}

dependencies {
    val okhttpVersion: String by project
    val mockitoVersionCore: String by project
    val mockitoVersionKtl: String by project
    val jupiterVersion: String by project

    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    implementation(project(":jsa-jackson-mapper"))
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersionCore")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersionKtl")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
}

