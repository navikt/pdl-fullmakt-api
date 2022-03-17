package no.nav.pdl.pdlfullmaktapi.config

import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder


class ApplicationContext {
    private val environment = Environment()

    val httpClient = HttpClientBuilder.build()
    val tokendingsService = TokendingsServiceBuilder.buildTokendingsService(maxCachedEntries = 10000)
    val tokendingsTokenFetcher = TokendingsTokenFetcher(tokendingsService, environment.fullmaktClientId)

}