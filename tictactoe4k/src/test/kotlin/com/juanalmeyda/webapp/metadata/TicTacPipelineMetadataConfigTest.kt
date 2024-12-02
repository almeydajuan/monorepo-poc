package com.juanalmeyda.webapp.metadata

import com.juanalmeyda.metadata.PipelineMetadataConfigTest
import com.juanalmeyda.metadata.yaml.CheckStep
import com.juanalmeyda.metadata.yaml.CheckoutStep
import com.juanalmeyda.metadata.yaml.CleanupGradleCacheStep
import com.juanalmeyda.metadata.yaml.GradleCachePackagesStep
import com.juanalmeyda.metadata.yaml.PipelineMetadata
import com.juanalmeyda.metadata.yaml.SetupJvmStep

class TicTacPipelineMetadataConfigTest : PipelineMetadataConfigTest {

    override val config = PipelineMetadata(
        name = "Tic Tac Toe 4k",
        pathPrefix = "tictactoe4k",
        pipelineSteps = listOf(
            CheckoutStep(),
            SetupJvmStep(),
            GradleCachePackagesStep(),
            CheckStep(projectName = "tictactoe4k"),
            CleanupGradleCacheStep()
        )
    )
}
