package no.nav.pdl.pdlfullmaktapi

import com.fasterxml.jackson.annotation.JsonProperty
import io.ktor.client.features.*
import io.ktor.client.request.get
import io.ktor.config.ApplicationConfig


data class Config (
   val tokenx: TokenX,

) {
    data class TokenX(
        val metadata: Metadata,
        val clientId: String,
    ) {
        data class Metadata(
            @JsonProperty("issuer") val issuer: String,
            @JsonProperty("jwks_uri") val jwks_uri: String,
        )
    }
}

suspend fun ApplicationConfig.load() = Config(
    tokenx = Config.TokenX(
        metadata = httpClient().get(property("tokenx.wellKnownUrl").getString()),
        clientId = property("tokenx.clientId").getString()
    ),
)

fun systemEnv(name: String) = System.getenv(name)
    ?: error("Mangler env var $name")
