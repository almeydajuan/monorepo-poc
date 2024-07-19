plugins {
    id("library")
}

dependencies {
    api(platform("dev.forkhandles:forkhandles-bom:_"))
    api("dev.forkhandles:values4k:_")

    implementation(Http4k.core)

    testApi(Testing.strikt.core)
}
