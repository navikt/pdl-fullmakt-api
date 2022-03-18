package no.nav.pdl.pdlfullmaktapi.config

import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.engine.*
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*

object HttpClientBuilder {

    fun build(setProxy: Boolean =false): HttpClient {
        return HttpClient(Apache) {
            install(JsonFeature) {
                serializer  = JacksonSerializer { configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)}
            }
            install(HttpTimeout)
            install(Logging) {
                level = LogLevel.INFO
            }

            if(setProxy){
                engine{
                    this.proxy = ProxyBuilder.http(requireProperty("HTTP_PROXY")) }
                }
        }
    }

}
