import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.`java-test-fixtures`

plugins {
    `java-test-fixtures`
}

dependencies {
    testFixturesApi(Testing.junit.jupiter.api)
    testFixturesImplementation("org.http4k:http4k-testing-approval:_")
    testFixturesImplementation("org.http4k:http4k-format-jackson-yaml:_")
}

