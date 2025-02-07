plugins {
    id("backend")
    id("io.ktor.plugin")
}

dependencies {
    implementation(Ktor.server.netty)
    implementation("io.ktor:ktor-server-core-jvm:_")
    implementation("io.ktor:ktor-server-content-negotiation:_")
    implementation("io.ktor:ktor-client-content-negotiation:_")
    implementation("io.ktor:ktor-serialization-kotlinx-json:_")
    implementation("ch.qos.logback:logback-classic:_")

    testImplementation("io.ktor:ktor-server-test-host:_")
    testApi(Testing.strikt.core)
}

application {
    mainClass = "com.juanalmeyda.featureflags.MainKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
