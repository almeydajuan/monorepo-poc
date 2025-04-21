package com.juanalmeyda.featureflags.repository

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

interface FeatureFlagRepositoryContractTest {
    val featureFlagRepository: FeatureFlagRepository

    @Test
    fun `when feature flag does not exist then it should be disabled`() {
        val featureFlag = featureFlagRepository.getFeatureFlag(TEST_FEATURE_FLAG_NAME)

        expectThat(featureFlag.enabled).isFalse()
    }

    @Test
    fun `when feature flag exists then it should be enabled`() {
        expectThat(featureFlagRepository.getFeatureFlag(TEST_FEATURE_FLAG_NAME).enabled).isFalse()

        featureFlagRepository.updateFeatureFlag(TEST_FEATURE_FLAG_NAME, true)

        expectThat(featureFlagRepository.getFeatureFlag(TEST_FEATURE_FLAG_NAME).enabled).isTrue()
    }

    companion object {
        const val TEST_FEATURE_FLAG_NAME = "test"
    }
}
