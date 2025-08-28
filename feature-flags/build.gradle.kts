plugins {
    alias(libs.plugins.io.ktor.plugin)
    id("backend")
    id("composed")
}

dependencies {
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.logback.classic)

    implementation(libs.jedis)

    testImplementation(libs.ktor.server.test.host)
    testApi(libs.strikt.core)
}

application {
    mainClass = "com.juanalmeyda.featureflags.MainKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
