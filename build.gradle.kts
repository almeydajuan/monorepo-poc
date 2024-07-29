plugins {
    id("org.barfuin.gradle.taskinfo")
}

group = "com.juanalmeyda"
version = "1.0-SNAPSHOT"

tasks.wrapper {
    gradleVersion = "8.9"
    distributionSha256Sum = "258e722ec21e955201e31447b0aed14201765a3bfbae296a46cf60b70e66db70"
    distributionType = Wrapper.DistributionType.ALL
}

