plugins {
    id("backend")
}

dependencies {
    implementation(platform(libs.http4k.bom))
    implementation(libs.http4k.core)
    implementation(libs.http4k.client.okhttp)
    implementation(libs.http4k.format.jackson)
    implementation(libs.http4k.template.handlebars)

    testApi(libs.strikt.core)
    testImplementation(libs.http4k.testing.approval)
    testImplementation(libs.http4k.format.jackson.yaml)
}

application {
    mainClass = "com.juanalmeyda.tictactoe4k.MainKt"
}
