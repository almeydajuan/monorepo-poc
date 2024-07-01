import de.fayard.refreshVersions.core.FeatureFlag.GRADLE_UPDATES

include("tictactoe4k", "metadata-generator")

rootProject.name = "monorepo-poc"

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.5"
}

refreshVersions {
    featureFlags {
        disable(GRADLE_UPDATES)
    }
    rejectVersionIf {
        @Suppress("UnstableApiUsage")
        candidate.stabilityLevel.isLessStableThan(current.stabilityLevel)
    }
}
