plugins {
    kotlin("jvm") version "2.1.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.5.14")
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.14")
    implementation("chat.giga:spring-ai-starter-model-gigachat:1.1.4")
//    implementation("chat.giga:gigachat-java:0.1.18")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.21.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.21.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}