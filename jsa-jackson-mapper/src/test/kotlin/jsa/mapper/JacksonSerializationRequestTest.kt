package jsa.mapper

import jsa.mapper.api.v1.models.CdvDebug
import jsa.mapper.api.v1.models.CdvRequest
import jsa.mapper.api.v1.models.CdvRequestDebugMode
import jsa.mapper.api.v1.models.CdvRequestDebugStubs
import jsa.mapper.api.v1.models.CdvSchedule
import jsa.mapper.api.v1.models.CdvSearchingObject
import jsa.mapper.api.v1.models.CdvSearchingRequest
import jsa.mapper.api.v1.models.CdvVisibility
import jsa.mapper.api.v1.models.TypeRequest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class JacksonSerializationRequestTest {

    private val requestId = UUID.randomUUID().toString()

    private val request = CdvSearchingRequest(
        requestType = TypeRequest.GET.toString(),
        requestId = requestId,
        debug = CdvDebug(
            mode = CdvRequestDebugMode.STUB,
            stub = CdvRequestDebugStubs.SUCCESS
        ),
        cdv = CdvSearchingObject(
            schedule = CdvSchedule.STANDARD,
            visibility = CdvVisibility.INTERNAL
        )
    )
    @Test
    fun serialize() {
        val json = apiV1RequestSerialize(request)
        assertContains(json, Regex("\"beg\":\\s*\"starting\""))
        assertContains(json, Regex("\"requestType\":\\s*\"GET\""))
        assertContains(json, Regex("\"requestId\":\\s*\"$requestId\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"schedule\":\\s*\"standard\""))
        assertContains(json, Regex("\"visibility\":\\s*\"internal\""))
    }
    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val rsl = apiV1Mapper.readValue(json, CdvRequest::class.java) as CdvSearchingRequest
        assertEquals(request, rsl)
    }

    @Test
    fun deserializeJsonText() {
        val tst = apiV1RequestDeserialize<CdvRequest>(json = """
        {
        "beg":"starting",
        "requestType":"GET",
        "requestId":"$requestId",
        "debug":{
            "mode":"stub",
            "stub":"success"
            },
        "cdv":{
            "schedule":"standard",
            "visibility":"internal"
            }
        }
    """.trimIndent())
        assertEquals(request, tst)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "555"}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, CdvSearchingRequest::class.java)
        assertEquals("555", obj.requestId)
    }
}