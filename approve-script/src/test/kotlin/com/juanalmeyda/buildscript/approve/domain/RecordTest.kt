package com.juanalmeyda.buildscript.approve.domain

import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.format.Jackson.json
import org.http4k.testing.ApprovalTest
import org.http4k.testing.Approver
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ApprovalTest::class)
class RecordTest {

    @Test
    fun `it prints data class as string`(approver: Approver) {
        val response = Response(Status.OK)
            .json(Record(1, "foo"))

        approver.assertApproved(response)
    }

}

