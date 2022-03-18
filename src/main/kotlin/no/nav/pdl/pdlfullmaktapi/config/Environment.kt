package no.nav.pdl.pdlfullmaktapi.config

data class Environment(

    /*val fullmaktClientId: String = System.getenv("FULLMAKT_CLIENT_ID")*/

    val fullmaktClientId: String = System.getenv("TOKEN_X_CLIENT_ID")


)

fun requireProperty(property: String) =
    getPropertyOrNull(property) ?: throw IllegalStateException("Missing required property $property")

fun getPropertyOrNull(property: String): String? =
    System.getProperty(property, System.getenv(property))
