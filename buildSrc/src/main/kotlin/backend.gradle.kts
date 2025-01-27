import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.util.TimeZone

plugins {
    application
    id("library")
    id("testFixtures")
    id("approve")
    id("metadata")
}

dependencies {
    api(project(":infra"))

    implementation(Http4k.server.jetty)

    testFixturesApi(testFixtures(project(":infra")))
}

tasks {
    check {
        val clock = Clock.system(TimeZone.getDefault().toZoneId())
        doFirst {
            extensions.extraProperties.set("startTime", clock.instant())
        }

        doLast {
            val startTime = extensions.extraProperties.get("startTime") as Instant
            extensions.extraProperties.set("duration", Duration.between(startTime, clock.instant()))
        }
    }
}


