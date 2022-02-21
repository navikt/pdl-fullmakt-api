package no.nav.pdl.pdlfullmaktapi.unleash

enum class Cluster {
    DEV_FSS, PROD_FSS;

    companion object {
        val current: Cluster by lazy {
            when (val c=System.getenv("NAIS_CLUSTER_NAME")){
                "dev-fss" -> DEV_FSS
                "prod-fss" -> PROD_FSS
                else -> throw RuntimeException("Ukjent cluster: $c")
            }
        }
    }

    fun asString(): String = name.lowercase().replace("_", "-")
}