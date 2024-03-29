package no.nav.pdl.pdlfullmaktapi

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import io.ktor.jackson.JacksonConverter
import io.ktor.request.*
import io.ktor.routing.*
import no.finn.unleash.FakeUnleash
import no.nav.pdl.pdlfullmaktapi.config.ApplicationContext
import no.nav.pdl.pdlfullmaktapi.routes.foedselsnummer
import no.nav.pdl.pdlfullmaktapi.routes.fullmakt
import no.nav.pdl.pdlfullmaktapi.routes.internal

import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

data class ApplicationStatus(var running: Boolean = true, var initialized: Boolean = false)

@Suppress("unused") // Referenced in application.conf
fun Application.module(appContext: ApplicationContext = ApplicationContext()){
    val baseUrl = "/person/pdl-fullmakt-api"
    val appContext = ApplicationContext()

    val config = runBlocking{ environment.config.load() }
    val applicationStatus = ApplicationStatus()
    installAuthentication(config.tokenx)

    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter())
    }

    install(CallLogging) {
        level = Level.TRACE
        filter { call -> !call.request.path().startsWith("$baseUrl/internal") }
    }

    routing {
        internal(readySelfTestCheck={applicationStatus.initialized}, aLiveSelfTestCheck={applicationStatus.running})
        when {
            isDev -> {
                val unleash = FakeUnleash().apply {
                    enableAll()
                }
                print("It is dev environment")
                fullmakt(baseUrl, appContext.externalHttpClient, "" )
            }
            isProd -> {
                authenticate("tokenX"){
                    foedselsnummer()
                }
            }
        }

    }
    applicationStatus.initialized = true
    log.debug("Application started")
}

val Application.envKind get() = environment.config.property("ktor.environment").getString()
val Application.isDev get() = envKind == "dev"
val Application.isProd get() = envKind != "dev"
