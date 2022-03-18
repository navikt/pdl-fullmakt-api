package no.nav.pdl.pdlfullmaktapi.routes

import io.ktor.application.call
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.routing.*
import no.nav.pdl.pdlfullmaktapi.config.proxy

internal fun Route.fullmakt(baseUrl: String, httpClient: HttpClient, accessToken: String){

    route("/person/pdl-fullmakt-api/{...}"){
        get{
            val subPath =  this.call.request.path().substringAfter("/person/pdl-fullmakt-api/")
            proxy(httpClient) {
                this.url("$baseUrl/$subPath")
                header("Authorization", "Bearer $accessToken")
                accept(ContentType.Application.Json)
                method = HttpMethod.Get
            }
        }
    }

}