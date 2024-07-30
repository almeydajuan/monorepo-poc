import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.`java-test-fixtures`

plugins {
    `java-test-fixtures`
}

dependencies {
    testFixturesApi(Testing.junit.jupiter.api)
    testFixturesImplementation(Http4k.testing.approval)
    testFixturesImplementation(Http4k.format.jacksonYaml)
}

