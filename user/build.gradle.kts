plugins {
    id("backend")
    id("composed")
}

dependencies {
    implementation(Http4k.core)
    implementation(Http4k.format.jackson)

    testApi(Testing.strikt.core)
}

application {
    mainClass = "com.juanalmeyda.user.MainKt"
}

