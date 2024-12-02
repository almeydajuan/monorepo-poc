package com.juanalmeyda.user.metadata

import com.juanalmeyda.metadata.PipelineMetadataConfigTest
import com.juanalmeyda.metadata.yaml.CheckUserStep
import com.juanalmeyda.metadata.yaml.CheckoutStep
import com.juanalmeyda.metadata.yaml.CleanupGradleCacheStep
import com.juanalmeyda.metadata.yaml.GradleCachePackagesStep
import com.juanalmeyda.metadata.yaml.PipelineMetadata
import com.juanalmeyda.metadata.yaml.SetupDockerStep
import com.juanalmeyda.metadata.yaml.SetupJvmStep

class UserPipelineMetadataConfigTest : PipelineMetadataConfigTest {

    override val config = PipelineMetadata(
        name = "User",
        pathPrefix = "user",
        pipelineSteps = listOf(
            CheckoutStep(),
            SetupJvmStep(),
            SetupDockerStep(),
            GradleCachePackagesStep(),
            CheckUserStep(),
            CleanupGradleCacheStep()
        )
    )
}
