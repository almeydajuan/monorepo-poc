plugins {
    id("composed")
}

dependencies {
    implementation(JetBrains.exposed.core)
    implementation(JetBrains.exposed.jdbc)
    implementation("org.postgresql:postgresql:_")
}
