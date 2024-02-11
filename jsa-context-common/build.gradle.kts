plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    implementation(project(":jsa-jackson-mapper"))
}

