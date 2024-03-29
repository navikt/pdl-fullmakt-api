package no.nav.pdl.pdlfullmaktapi

import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.interfaces.Payload
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.auth.*
import java.net.URL
import java.util.concurrent.TimeUnit

fun Application.installAuthentication(config: Config.TokenX) {

    val jwkProvider = JwkProviderBuilder(URL(config.metadata.jwks_uri))
            // cache 10 JWKs for 24 hours
        .cached(10,24,TimeUnit.HOURS)
            // if not cached, allow max 10 different keys per minute to be fetched from external provider
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    install(Authentication){
        jwt("tokenX"){
            verifier(jwkProvider, config.metadata.issuer)
            realm = "pdl-fullmakt-api"
            authHeader {
                val cookie = it.request.cookies["selvbetjening-idtoken"]
                    ?: throw CookieNotSetException("Cookie with name selvbetjening-idtoken not found")
                HttpAuthHeader.Single("Bearer", cookie)
            }
            validate { credentials ->
                try{
                    requireNotNull(credentials.payload.getSubjectFromPidOrSub()) {
                        "Auth: Missing subject in token"
                    }
                    JWTPrincipal(credentials.payload)
                } catch (e: Throwable) {
                    null
                }

            }
        }
    }

}

private fun Payload.getSubjectFromPidOrSub(): String? {
    return this.getClaim("pid").asString() ?: this.getClaim("sub").asString()
}

class CookieNotSetException(override val message: String?) : RuntimeException(message)
