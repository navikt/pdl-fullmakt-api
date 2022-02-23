package no.nav.pdl.pdlfullmaktapi

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import no.nav.pdl.pdlfullmaktapi.plugins.configureRouting
import io.ktor.jackson.JacksonConverter
import io.ktor.request.*
import io.ktor.routing.*
import no.nav.pdl.pdlfullmaktapi.routes.internal
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

data class ApplicationStatus(var running: Boolean = true, var initialized: Boolean = false)

@Suppress("unused") // Referenced in application.conf
fun Application.module(){
    val config = runBlocking{ environment.config.load() }
    val applicationStatus = ApplicationStatus()
    installAuthentication(config.tokenx)

    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter())
    }

    install(CallLogging) {
        level = Level.TRACE
        filter { call -> !call.request.path().startsWith("/internal") }
    }

    routing {
        internal(readySelfTestCheck={applicationStatus.initialized}, aLiveSelfTestCheck={applicationStatus.running})

    }
    applicationStatus.initialized = true

}

