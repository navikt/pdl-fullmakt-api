val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val unleashClientJavaVersion = "4.4.1"

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "no.nav.pdl"
version = "0.0.1-SNAPSHOT"
application {
    mainClass.set("no.nav.pdl.pdlfullmaktapi.PdlFullmaktApiApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("no.finn.unleash:unleash-client-java:$unleashClientJavaVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}