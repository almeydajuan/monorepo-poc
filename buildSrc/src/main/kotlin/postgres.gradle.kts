plugins {
    id("composed")
    id("flyway")
}

dependencies {
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgresql)
}

tasks {
    named("testsWithDatabase") {
        dependsOn("flywayMigrate")
    }
}
