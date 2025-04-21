package com.juanalmeyda.featureflags.repository

class InMemoryFeatureFlagRepositoryTest : FeatureFlagRepositoryContractTest {
    override val featureFlagRepository = InMemoryFeatureFlagRepository()
}
