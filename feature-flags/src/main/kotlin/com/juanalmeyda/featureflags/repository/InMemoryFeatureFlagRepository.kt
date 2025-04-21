package com.juanalmeyda.featureflags.repository

import com.juanalmeyda.featureflags.FeatureFlag

class InMemoryFeatureFlagRepository : FeatureFlagRepository {
    private val flags = mutableMapOf<String, Boolean>()

    override fun getFeatureFlag(id: String): FeatureFlag =
        flags[id]
            ?.let { FeatureFlag(id, it) }
            ?: FeatureFlag(id, false)

    override fun updateFeatureFlag(id: String, enabled: Boolean) {
        flags.put(id, enabled)
    }
}
