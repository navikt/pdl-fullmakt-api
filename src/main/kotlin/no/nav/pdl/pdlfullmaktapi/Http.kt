package no.nav.pdl.pdlfullmaktapi

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.http.*


fun httpClient() = HttpClient(Apache){
    install(JsonFeature) {
        serializer  = JacksonSerializer { configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)}
    }
    install(Logging) {
        level = LogLevel.INFO
    }
}

fun ApplicationCall.getTokenInfo(): Map<String, JsonNode> = authentication
    .principal<JWTPrincipal>()
    ?.let { principal ->
        principal.payload.claims.entries
            .associate { claim -> claim.key to claim.value.`as`(JsonNode::class.java) }
    } ?: error("No JWT principal found in request")

val HttpHeaders.NavCallId: String
    get() = "Nav-Call-Id"
val HttpHeaders.NavConsumerId: String
    get() = "Nav-Consumer-Id"
val HttpHeaders.NavPersonIdenter: String
    get() = "Nav-Personidenter"
