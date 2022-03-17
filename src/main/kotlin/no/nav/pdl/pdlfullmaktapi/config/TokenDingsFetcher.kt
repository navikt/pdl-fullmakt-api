package no.nav.pdl.pdlfullmaktapi.config

import no.nav.tms.token.support.tokendings.exchange.TokendingsService

class TokendingsTokenFetcher(
    private val tokendingsService: TokendingsService,
    private val fullmaktClientId: String
) {
    suspend fun getFullmaktToken(userToken: String): String {
        return tokendingsService.exchangeToken(userToken, fullmaktClientId)
    }
}
