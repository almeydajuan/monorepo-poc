plugins {
    id("org.barfuin.gradle.taskinfo") version "2.2.0"
}

group = "com.juanalmeyda"
version = "1.0-SNAPSHOT"

tasks.wrapper {
    gradleVersion = "8.8"
    distributionSha256Sum = "f8b4f4772d302c8ff580bc40d0f56e715de69b163546944f787c87abf209c961"
    distributionType = Wrapper.DistributionType.ALL
}

