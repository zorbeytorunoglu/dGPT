plugins {
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
}

group = "com.zorbeytorunoglu"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.dv8tion:JDA:5.0.0-beta.5")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.theokanning.openai-gpt3-java:client:0.11.1")
    implementation("com.theokanning.openai-gpt3-java:api:0.11.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}