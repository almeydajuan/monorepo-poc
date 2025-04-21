package com.juanalmeyda.featureflags.repository

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

interface FeatureFlagRepositoryContractTest {
    val featureFlagRepository: FeatureFlagRepository

    @Test
    fun `when feature flag does not exist then it should be disabled`() {
        val featureFlag = featureFlagRepository.getFeatureFlag("test")

        expectThat(featureFlag.enabled).isFalse()
    }

    @Test
    fun `when feature flag exists then it should be enabled`() {
        val featureFlagName = "test"
        expectThat(featureFlagRepository.getFeatureFlag(featureFlagName).enabled).isFalse()

        featureFlagRepository.updateFeatureFlag(featureFlagName, true)

        expectThat(featureFlagRepository.getFeatureFlag(featureFlagName).enabled).isTrue()
    }
}
