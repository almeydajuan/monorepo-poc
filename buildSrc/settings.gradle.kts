pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("de.fayard.refreshVersions") version "0.60.5"
        id("dev.panuszewski.typesafe-conventions") version "0.7.4"
    }
}

plugins {
    id("de.fayard.refreshVersions")
    id("dev.panuszewski.typesafe-conventions")
}
