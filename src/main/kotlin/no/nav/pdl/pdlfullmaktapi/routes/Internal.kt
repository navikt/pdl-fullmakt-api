package no.nav.pdl.pdlfullmaktapi.routes

import io.ktor.application.call
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat


const val INTERNAL = "/internal"
const val IS_ALIVE = "/isAlive"
const val IS_READY = "/isReady"
const val METRICS = "/metrics"

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
        get(METRICS){
            val names = call.request.queryParameters.getAll("name[]")?.toSet() ?: emptySet()
            call.respondTextWriter(ContentType.parse(TextFormat.CONTENT_TYPE_004)) {
                TextFormat.write004(this, CollectorRegistry.defaultRegistry.filteredMetricFamilySamples(names))
            }
        }

    }


}