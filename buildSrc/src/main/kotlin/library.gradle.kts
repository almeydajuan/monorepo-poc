plugins {
    id("kotlin")
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:_"))
    api(platform("dev.forkhandles:forkhandles-bom:_"))
    api("dev.forkhandles:values4k:_")
    api("dev.forkhandles:result4k:_")

    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform {
            excludeTags(composedTestTag)
        }
    }
}
