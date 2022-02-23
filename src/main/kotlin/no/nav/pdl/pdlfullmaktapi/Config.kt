package no.nav.pdl.pdlfullmaktapi

import com.fasterxml.jackson.annotation.JsonProperty

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