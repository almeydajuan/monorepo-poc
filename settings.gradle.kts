import de.fayard.refreshVersions.core.FeatureFlag.VERSIONS_CATALOG

include("tictactoe4k", "metadata-generator")

rootProject.name = "monorepo-poc"

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.5"
}

refreshVersions {
    featureFlags {
        disable(VERSIONS_CATALOG)
    }
    rejectVersionIf {
        @Suppress("UnstableApiUsage")
        candidate.stabilityLevel.isLessStableThan(current.stabilityLevel)
    }
}
