import gradle.kotlin.dsl.accessors._3b079f6fc8b6f3b1a950c98983d6005c.implementation

plugins {
    id("composed")
}

dependencies {
    implementation(JetBrains.exposed.core)
    implementation(JetBrains.exposed.jdbc)
    implementation("org.postgresql:postgresql:_")
}
