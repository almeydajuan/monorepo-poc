plugins {
    id("backend")
    id("database")
}

dependencies {
    implementation(Http4k.core)
    implementation(Http4k.format.jackson)

    implementation(JetBrains.exposed.core)
    implementation(JetBrains.exposed.jdbc)
    implementation("org.postgresql:postgresql:_")

    testApi(Testing.strikt.core)
}

application {
    mainClass = "com.juanalmeyda.user.MainKt"
}

