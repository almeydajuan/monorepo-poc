pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("de.fayard.refreshVersions") version "0.60.6"
        id("dev.panuszewski.typesafe-conventions") version "0.7.4"
////                                           # available:"0.8.0-RC1"
    }
}

plugins {
    id("de.fayard.refreshVersions")
    id("dev.panuszewski.typesafe-conventions")
}
