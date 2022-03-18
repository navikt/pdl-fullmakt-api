package no.nav.pdl.pdlfullmaktapi.config

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.OutgoingContent
import io.ktor.request.path
import io.ktor.request.receiveStream
import io.ktor.response.respond
import io.ktor.util.filter
import io.ktor.util.pipeline.PipelineContext
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.copyAndClose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging

private val LOGGER = KotlinLogging.logger {}
private val sikkerLogg = KotlinLogging.logger("tjenestekall")

internal suspend fun PipelineContext<*, ApplicationCall>.proxy(
    client: HttpClient,
    block: HttpRequestBuilder.() -> Unit = {}
) {
    try {
        val callBytes = call.receiveBytes()
        val response = client.request<HttpResponse> {
            body = callBytes
            block()
        }
        call.pipeResponse(response)
    } catch (e: Exception) {
        LOGGER.error("Proxy error mot: ${call.request.path()}. Se sikkerlogg for detaljer")
        sikkerLogg.error("Proxy error", e)
        throw e
    }
}

private suspend fun ApplicationCall.receiveBytes(): ByteArray = withContext(Dispatchers.IO) {
    receiveStream().use {
        it.readBytes()
    }
}

internal fun ApplicationCall.subPath(delimiter: String) = this.request.path().substringAfter(delimiter)

private suspend fun ApplicationCall.pipeResponse(response: HttpResponse) {
    val proxiedHeaders = response.headers
    val contentType = proxiedHeaders[HttpHeaders.ContentType]
    val contentLength = proxiedHeaders[HttpHeaders.ContentLength]

    respond(object : OutgoingContent.WriteChannelContent() {
        override val contentLength: Long? = contentLength?.toLong()
        override val contentType: ContentType? = contentType?.let { ContentType.parse(it) }
        override val headers: Headers = Headers.build {
            appendAll(
                proxiedHeaders.filter { key, _ ->
                    !key.equals(
                        HttpHeaders.ContentType,
                        ignoreCase = true
                    ) && !key.equals(HttpHeaders.ContentLength, ignoreCase = true) &&
                        !key.equals(HttpHeaders.TransferEncoding, ignoreCase = true)
                }
            )
        }
        override val status: HttpStatusCode = response.status
        override suspend fun writeTo(channel: ByteWriteChannel) {
            response.content.copyAndClose(channel)
        }
    })
}
