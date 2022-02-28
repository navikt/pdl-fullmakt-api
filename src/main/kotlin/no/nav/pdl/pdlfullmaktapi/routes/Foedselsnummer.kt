package no.nav.pdl.pdlfullmaktapi.routes

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.interfaces.Payload
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import no.nav.pdl.pdlfullmaktapi.getTokenInfo

fun Route.foedselsnummer(){
    route("/fodselsnr"){
        get{
            withContext(IO) {
                val foedselsnummer =  call.getTokenInfo()["pid"]?.asText() ?: call.getTokenInfo()["sub"]?.asText() ?: "subject does not exist"
                call.respond(HttpStatusCode.OK, foedselsnummer)
            }
        }
    }
}
