package jsa.mapper

import jsa.mapper.api.v1.models.Address
import jsa.mapper.api.v1.models.CdvPermissions
import jsa.mapper.api.v1.models.CdvResponse
import jsa.mapper.api.v1.models.CdvResponseObject
import jsa.mapper.api.v1.models.CdvStartingResponse
import jsa.mapper.api.v1.models.Employer
import jsa.mapper.api.v1.models.ResponseResult
import jsa.mapper.api.v1.models.Salary
import jsa.mapper.api.v1.models.TypeLink
import java.math.BigDecimal

import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class JacksonSerializationResponseTest {

    private val requestId = UUID.randomUUID().toString()
    private val response = CdvStartingResponse(
            whereExecuteSearching = TypeLink.HH,
            responseType = "searching",
            requestId,
            result = ResponseResult.SUCCESS,
            errors = listOf(),
            lock = "optimistic",
            permissions = setOf(CdvPermissions.START),
            cdv = listOf(
                CdvResponseObject(
                    id = "TrdFFG12fgh",
                    name = "c++ dev",
                    area = "Moscow",
                    salary = Salary(
                        100,
                        150,
                        "RUB",
                        true
                    ),
                    address = Address(
                        city = "Moscow",
                        street = null,
                        lat = BigDecimal.valueOf(15.12312),
                        lng = BigDecimal.valueOf(128.12312),
                        raw = null
                    ),
                    published = "12.12.2023",
                    created = null,
                    url = "https://api.hh.ru",
                    employer = Employer(
                        id = "gfhDFfdfsdf45fg",
                        name = "SBER",
                        url = "https://sber.ru"
                    ),
                    snippet = null,
                    contact = null,
                    schedule = null,
                    experience = "1 to 3",
                )
            )
        )

    @Test
    fun serResp() {
        val json = apiV1ResponseSerialize(response)
        assertContains(json, Regex("\"whereExecuteSearching\":\\s*\"HH\""))
        assertContains(json, Regex("\"responseType\":\\s*\"searching\""))
        assertContains(json, Regex("\"requestId\":\\s*\"$requestId\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"lock\":\\s*\"optimistic\""))
    }

    @Test
    fun desResp() {
        val jsStrEx = apiV1ResponseDeserialize<CdvResponse>("""
            {
            "requestType":"start",
            "whereExecuteSearching":"HH",
            "responseType":"searching","requestId":"$requestId","result":"success","errors":[],"id":null,"lock":"optimistic","permissions":["start"],"cdv":[{"id":"TrdFFG12fgh","name":"c++ dev","area":"Moscow","salary":{"from":100,"to":150,"currency":"RUB","gross":true},"address":{"city":"Moscow","street":null,"lat":15.12312,"lng":128.12312,"raw":null},"published":"12.12.2023","created":null,"url":"https://api.hh.ru","employer":{"id":"gfhDFfdfsdf45fg","name":"SBER","url":"https://sber.ru"},"snippet":null,"contact":null,"schedule":null,"experience":"1 to 3"}]
            }
        """.trimIndent())
        assertEquals(response, jsStrEx)
    }
}
