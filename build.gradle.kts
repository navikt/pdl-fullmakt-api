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
    mainClass.set("no.nav.pdl.pdlfullmaktapi.ApplicationKt")
}

repositories {
    mavenCentral()
}

fun ktor(name: String) = "io.ktor:ktor-$name:$ktor_version"

dependencies {
    // Logging
    implementation("io.github.microutils:kotlin-logging:2.1.21")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Ktor Server
    implementation(ktor("server-core"))
    implementation(ktor("server-netty"))
    implementation(ktor("auth"))
    implementation(ktor("auth-jwt"))

    // Ktor Client
    implementation(ktor("client-core"))
    implementation(ktor("client-apache"))
    implementation(ktor("client-jackson"))
    implementation(ktor("client-logging"))
    implementation(ktor("client-auth"))

    implementation("no.finn.unleash:unleash-client-java:$unleashClientJavaVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")