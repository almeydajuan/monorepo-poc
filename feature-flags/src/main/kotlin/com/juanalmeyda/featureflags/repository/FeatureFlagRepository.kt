package com.juanalmeyda.featureflags.repository

import com.juanalmeyda.featureflags.FeatureFlag

interface FeatureFlagRepository {
    fun getFeatureFlag(id: String): FeatureFlag
    fun updateFeatureFlag(id: String, enabled: Boolean)
}
