plugins {
    id("kotlin")
}

dependencies {
    implementation(platform(libs.http4k.bom))
    api(platform(libs.forkhandles.bom))
    api(libs.values4k)
    api(libs.result4k)

    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform {
            excludeTags(composedTestTag)
        }
    }
}
