val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val micrometer_version: String by project
val unleashClientJavaVersion = "4.4.1"
val tms_ktor_token_version = "2022.02.10-11.14-86260e6f073b"

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "no.nav.pdl.pdlfullmaktapi"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven ("https://jitpack.io")
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
    implementation(ktor("jackson"))

    // Ktor Client
    implementation(ktor("client-core"))
    implementation(ktor("client-apache"))
    implementation(ktor("client-jackson"))
    implementation(ktor("client-logging"))
    implementation(ktor("client-auth"))

    implementation("com.github.navikt.tms-ktor-token-support:token-support-tokendings-exchange:$tms_ktor_token_version")
    implementation("no.finn.unleash:unleash-client-java:$unleashClientJavaVersion")
    implementation("io.micrometer:micrometer-registry-prometheus:$micrometer_version")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.named<Jar>("jar") {
    archiveBaseName.set("app")

    manifest {
        attributes["Main-Class"] = application.mainClass
        attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
            it.name
        }
    }

    doLast {
        configurations.runtimeClasspath.get().forEach {
            val file = File("$buildDir/libs/${it.name}")
            if (!file.exists())
                it.copyTo(file)
        }
    }
}
