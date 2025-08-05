plugins {
    id("backend")
    id("postgres")
}

dependencies {
    implementation(platform(libs.http4k.bom))
    implementation(libs.http4k.core)
    implementation(libs.http4k.format.jackson)

    testApi(libs.strikt.core)
}

application {
    mainClass = "com.juanalmeyda.user.MainKt"
}

