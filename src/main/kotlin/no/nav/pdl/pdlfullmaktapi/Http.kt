package no.nav.pdl.pdlfullmaktapi

import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging

fun httpClient() = HttpClient(Apache){
    install(JsonFeature) {
        serializer  = JacksonSerializer { configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)}
    }
    install(Logging) {
        level = LogLevel.INFO
    }
}