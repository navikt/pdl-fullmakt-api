package no.nav.pdl.pdlfullmaktapi.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.*

const val INTERNAL = "/internal"
const val IS_ALIVE = "/isAlive"
const val IS_READY = "/isReady"


inline fun Route.internal(
    crossinline readySelfTestCheck: () -> Boolean,
    crossinline aLiveSelfTestCheck: () -> Boolean = {true}
) {
    route(INTERNAL){
        get(IS_ALIVE){
            if (aLiveSelfTestCheck()) {
                call.respondText("Alive")
            } else {
                call.respondText("Dead!", status = HttpStatusCode.InternalServerError)
            }
        }
        get(IS_READY){
            if (readySelfTestCheck()) {
                call.respondText("Ready")
            } else {
                call.respondText("Not Ready!", status = HttpStatusCode.InternalServerError)
            }
        }

    }


}