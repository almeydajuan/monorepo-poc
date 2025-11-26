pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("de.fayard.refreshVersions") version "0.60.6"
        id("dev.panuszewski.typesafe-conventions") version "0.10.0"
    }
}

plugins {
    id("de.fayard.refreshVersions")
    id("dev.panuszewski.typesafe-conventions")
}
