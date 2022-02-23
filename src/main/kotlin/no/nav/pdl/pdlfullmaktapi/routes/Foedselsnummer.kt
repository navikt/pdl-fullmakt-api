package no.nav.pdl.pdlfullmaktapi.routes

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.interfaces.Payload
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import no.finn.unleash.Unleash
import no.nav.pdl.pdlfullmaktapi.getTokenInfo
import no.nav.pdl.pdlfullmaktapi.unleash.IDPORTEN

fun Route.foedselsnummer(unleash: Unleash){
    route("/fodselsnr"){
        get{
            withContext(IO) {
                val foedselsnummer = if (unleash.isEnabled(IDPORTEN)) {
                    call.getTokenInfo()["pid"]?.asText() ?: error("Could not find 'pid' claim in token")
                } else {
                    call.getTokenInfo()["sub"]?.asText() ?: error("Could not find 'sub' claim in token")
                }
                call.respond(HttpStatusCode.OK, foedselsnummer)
            }
        }
    }
}
