package no.nav.pdl.pdlfullmaktapi.unleash

import no.finn.unleash.DefaultUnleash
import no.finn.unleash.Unleash
import no.finn.unleash.strategy.Strategy
import no.finn.unleash.util.UnleashConfig


const val IDPORTEN = "digihot.idporten"

class UnleashConfig {

    companion object {
        private val config: UnleashConfig = UnleashConfig.builder()
            .appName("")
            .instanceId("")
            .unleashAPI("https://unleash.nais.io/api/")
            .build()
        val unleash: Unleash = DefaultUnleash(config, ByClusterStrategy(Cluster.current))
    }



    class ByClusterStrategy(private val currentCluster: Cluster) : Strategy {
        override fun getName(): String = "byCluster"

        override fun isEnabled(parameters: Map<String, String>?): Boolean {
            val clustersParameter = parameters?.get("cluster") ?: return false
            val alleClustere = clustersParameter.split(",").map { it.trim() }.map { it.lowercase() }.toList()
            return alleClustere.contains(currentCluster.asString())
        }
    }
}