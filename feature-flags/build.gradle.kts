plugins {
    id("backend")
    id("io.ktor.plugin") version "3.0.3"
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:3.0.3")
    implementation("io.ktor:ktor-server-netty:3.0.3")
    implementation("ch.qos.logback:logback-classic:1.4.14")
}

application {
    mainClass = "com.juanalmeyda.featureflags.MainKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
