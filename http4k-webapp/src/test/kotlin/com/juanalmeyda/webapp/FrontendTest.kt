package com.juanalmeyda.webapp

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ClientFilters.FollowRedirects
import org.http4k.testing.ApprovalTest
import org.http4k.testing.Approver
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@ExtendWith(ApprovalTest::class)
class FrontendTest {

    val frontend = FollowRedirects().then(
        newFrontend(newBackend(Game()))
    )

    @Test
    fun `state of an empty game`(approver: Approver) {
        val response = frontend(Request(GET, "/"))

        expectThat(response.status).isEqualTo(OK)
        approver.assertApproved(response)
    }

    @Test
    fun `players take turns on each move`(approver: Approver) {
        frontend(Request(GET, "/move/0/1"))
        frontend(Request(GET, "/move/2/0"))
        val response = frontend(Request(GET, "/move/1/1"))

        expectThat(response.status).isEqualTo(OK)
        approver.assertApproved(response)
    }

    @Test
    fun `player X wins`(approver: Approver) {
        val frontend = newFrontend(newBackend(finishedGame))
        val response = frontend(Request(GET, "/"))

        expectThat(response.status).isEqualTo(OK)
        approver.assertApproved(response)
    }

    @Test
    fun `restart game`(approver: Approver) {
        val frontend = FollowRedirects().then(newFrontend(newBackend(finishedGame)))
        val response = frontend(Request(GET, "/restart"))

        expectThat(response.status).isEqualTo(OK)
        approver.assertApproved(response)
    }
}
