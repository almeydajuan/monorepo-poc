plugins {
    id("backend")
    id("io.ktor.plugin")
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:_")
    implementation(Ktor.server.netty)
    implementation("ch.qos.logback:logback-classic:_")
}

application {
    mainClass = "com.juanalmeyda.featureflags.MainKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
