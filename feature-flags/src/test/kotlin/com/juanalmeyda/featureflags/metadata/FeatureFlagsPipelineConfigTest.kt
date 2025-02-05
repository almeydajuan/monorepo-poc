package com.juanalmeyda.featureflags.metadata

import com.juanalmeyda.metadata.PipelineMetadataConfigTest
import com.juanalmeyda.metadata.yaml.CheckStep
import com.juanalmeyda.metadata.yaml.CheckoutStep
import com.juanalmeyda.metadata.yaml.CleanupGradleCacheStep
import com.juanalmeyda.metadata.yaml.GradleCachePackagesStep
import com.juanalmeyda.metadata.yaml.PipelineMetadata
import com.juanalmeyda.metadata.yaml.SetupJvmStep

class FeatureFlagsPipelineConfigTest : PipelineMetadataConfigTest {

    override val config = PipelineMetadata(
        name = "Feature Flags",
        pathPrefix = "feature-flags",
        pipelineSteps = listOf(
            CheckoutStep(),
            SetupJvmStep(),
            GradleCachePackagesStep(),
            CheckStep(projectName = "feature-flags"),
            CleanupGradleCacheStep()
        )
    )
}
