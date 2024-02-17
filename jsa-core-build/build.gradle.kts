plugins {
    kotlin("jvm")
}

val okhttpVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    implementation(project(":jsa-jackson-mapper"))
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
}

