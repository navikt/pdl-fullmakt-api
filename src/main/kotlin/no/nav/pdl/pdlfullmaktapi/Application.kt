package no.nav.pdl.pdlfullmaktapi

import io.ktor.application.*
import no.nav.pdl.pdlfullmaktapi.plugins.configureRouting

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module(){
    configureRouting()
}