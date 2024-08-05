plugins {
    id("library")
    id("testFixtures")
}

dependencies {
    api(platform("dev.forkhandles:forkhandles-bom:_"))
    api("dev.forkhandles:values4k:_")

    testApi(Testing.strikt.core)
}
