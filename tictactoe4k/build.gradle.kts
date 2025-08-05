plugins {
    id("backend")
}

dependencies {
    implementation(platform(libs.http4k.bom))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-format-jackson")
    implementation("org.http4k:http4k-template-handlebars")

    testApi(libs.strikt.core)
    testImplementation("org.http4k:http4k-testing-approval")
    testImplementation("org.http4k:http4k-format-jackson-yaml")
}

application {
    mainClass = "com.juanalmeyda.tictactoe4k.MainKt"
}
