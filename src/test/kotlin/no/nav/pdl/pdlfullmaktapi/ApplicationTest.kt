package no.nav.pdl.pdlfullmaktapi

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {

	// Starting point for a Ktor app:
	routing {
		get("/") {
			call.respondText("Hello World!")
		}
	}
	routing {
	}
}
