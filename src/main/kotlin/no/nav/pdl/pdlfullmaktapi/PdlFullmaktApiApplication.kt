package no.nav.pdl.pdlfullmaktapi

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import no.nav.pdl.pdlfullmaktapi.plugins.configureRouting

fun main(args: Array<String>) {
	embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
		configureRouting()
	}.start(wait = true)
}
