plugins {
    id("backend")
    alias(libs.plugins.ktor)
    id("composed")
}

dependencies {
    implementation(platform(libs.ktor.bom))
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-serialization-jackson")
    implementation(libs.logback.classic)

    implementation(libs.jedis)

    testImplementation("io.ktor:ktor-server-test-host")
    testApi(libs.strikt.core)
}

application {
    mainClass = "com.juanalmeyda.featureflags.MainKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
