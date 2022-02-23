package no.nav.pdl.pdlfullmaktapi

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import no.nav.pdl.pdlfullmaktapi.plugins.configureRouting

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module(){
    val config = runBlocking{ environment.config.load() }
    installAuthentication(config.tokenx)

/*    install(ContentNegotiation){
        register(ContentType.Application.Json, JsonConverter())
    }*/

    configureRouting()

}

