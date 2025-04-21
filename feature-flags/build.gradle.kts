plugins {
    id("backend")
    id("io.ktor.plugin")
    id("composed")
}

dependencies {
    implementation(Ktor.server.netty)
    implementation("io.ktor:ktor-server-core-jvm:_")
    implementation(Ktor.server.contentNegotiation)
    implementation(Ktor.client.contentNegotiation)
    implementation(Ktor.plugins.serialization.jackson)
    implementation("ch.qos.logback:logback-classic:_")

    implementation("redis.clients:jedis:_")

    testImplementation(Ktor.server.testHost)
    testApi(Testing.strikt.core)
}

application {
    mainClass = "com.juanalmeyda.featureflags.MainKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
