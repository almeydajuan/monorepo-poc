import de.fayard.refreshVersions.core.FeatureFlag.GRADLE_UPDATES
import de.fayard.refreshVersions.core.FeatureFlag.VERSIONS_CATALOG

include("tictactoe4k", "metadata-generator", "user")

rootProject.name = "monorepo-poc"

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.5"
}

refreshVersions {
    featureFlags {
        disable(GRADLE_UPDATES)
        disable(VERSIONS_CATALOG)
    }
    rejectVersionIf {
        @Suppress("UnstableApiUsage")
        candidate.stabilityLevel.isLessStableThan(current.stabilityLevel)
    }
}
