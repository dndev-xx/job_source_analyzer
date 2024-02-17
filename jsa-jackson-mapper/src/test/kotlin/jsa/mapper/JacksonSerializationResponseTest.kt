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
import kotlin.test.assertNotNull

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

    @Test
    fun desReqForHHModel() {
        val json = "{\n" +
                "\"items\": [\n" +
                "{\n" +
                "\"id\": \"93243493\",\n" +
                "\"premium\": false,\n" +
                "\"name\": \"Junior Java Developer\",\n" +
                "\"department\": null,\n" +
                "\"has_test\": false,\n" +
                "\"response_letter_required\": false,\n" +
                "\"area\": {\n" +
                "\"id\": \"1002\",\n" +
                "\"name\": \"Минск\",\n" +
                "\"url\": \"https://api.hh.ru/areas/1002\"\n" +
                "},\n" +
                "\"salary\": null,\n" +
                "\"type\": {\n" +
                "\"id\": \"open\",\n" +
                "\"name\": \"Открытая\"\n" +
                "},\n" +
                "\"address\": {\n" +
                "\"city\": \"Минск\",\n" +
                "\"street\": \"улица Платонова\",\n" +
                "\"building\": \"49\",\n" +
                "\"lat\": 53.91434,\n" +
                "\"lng\": 27.602022,\n" +
                "\"description\": null,\n" +
                "\"raw\": \"Минск, улица Платонова, 49\",\n" +
                "\"metro\": {\n" +
                "\"station_name\": \"Академия наук\",\n" +
                "\"line_name\": \"Московская\",\n" +
                "\"station_id\": \"62.407\",\n" +
                "\"line_id\": \"62\",\n" +
                "\"lat\": 53.92187,\n" +
                "\"lng\": 27.599066\n" +
                "},\n" +
                "\"metro_stations\": [\n" +
                "{\n" +
                "\"station_name\": \"Академия наук\",\n" +
                "\"line_name\": \"Московская\",\n" +
                "\"station_id\": \"62.407\",\n" +
                "\"line_id\": \"62\",\n" +
                "\"lat\": 53.92187,\n" +
                "\"lng\": 27.599066\n" +
                "}\n" +
                "],\n" +
                "\"id\": \"14997167\"\n" +
                "},\n" +
                "\"response_url\": null,\n" +
                "\"sort_point_distance\": null,\n" +
                "\"published_at\": \"2024-02-14T21:33:10+0300\",\n" +
                "\"created_at\": \"2024-02-14T21:33:10+0300\",\n" +
                "\"archived\": false,\n" +
                "\"apply_alternate_url\": \"https://hh.ru/applicant/vacancy_response?vacancyId=93243493\",\n" +
                "\"show_logo_in_search\": null,\n" +
                "\"insider_interview\": null,\n" +
                "\"url\": \"https://api.hh.ru/vacancies/93243493?host=hh.ru\",\n" +
                "\"alternate_url\": \"https://hh.ru/vacancy/93243493\",\n" +
                "\"relations\": [],\n" +
                "\"employer\": {\n" +
                "\"id\": \"756093\",\n" +
                "\"name\": \"ФП-ТРЕЙД\",\n" +
                "\"url\": \"https://api.hh.ru/employers/756093\",\n" +
                "\"alternate_url\": \"https://hh.ru/employer/756093\",\n" +
                "\"logo_urls\": {\n" +
                "\"90\": \"https://hhcdn.ru/employer-logo/6504849.png\",\n" +
                "\"240\": \"https://hhcdn.ru/employer-logo/6504850.png\",\n" +
                "\"original\": \"https://hhcdn.ru/employer-logo-original/1221102.png\"\n" +
                "},\n" +
                "\"vacancies_url\": \"https://api.hh.ru/vacancies?employer_id=756093\",\n" +
                "\"accredited_it_employer\": false,\n" +
                "\"trusted\": true\n" +
                "},\n" +
                "\"snippet\": {\n" +
                "\"requirement\": \"Вы нам подходите, если : У вас есть опыт разработки на <highlighttext>Java</highlighttext>. Будет плюсом и преимуществом: Законченные профильные курсы или стажировка. \",\n" +
                "\"responsibility\": null\n" +
                "},\n" +
                "\"contacts\": null,\n" +
                "\"schedule\": {\n" +
                "\"id\": \"flexible\",\n" +
                "\"name\": \"Гибкий график\"\n" +
                "},\n" +
                "\"working_days\": [],\n" +
                "\"working_time_intervals\": [],\n" +
                "\"working_time_modes\": [],\n" +
                "\"accept_temporary\": false,\n" +
                "\"professional_roles\": [\n" +
                "{\n" +
                "\"id\": \"96\",\n" +
                "\"name\": \"Программист, разработчик\"\n" +
                "}\n" +
                "],\n" +
                "\"accept_incomplete_resumes\": false,\n" +
                "\"experience\": {\n" +
                "\"id\": \"noExperience\",\n" +
                "\"name\": \"Нет опыта\"\n" +
                "},\n" +
                "\"employment\": {\n" +
                "\"id\": \"full\",\n" +
                "\"name\": \"Полная занятость\"\n" +
                "},\n" +
                "\"adv_response_url\": null,\n" +
                "\"is_adv_vacancy\": false,\n" +
                "\"adv_context\": null\n" +
                "}\n" +
                "],\n" +
                "\"found\": 7093,\n" +
                "\"pages\": 2000,\n" +
                "\"page\": 1,\n" +
                "\"per_page\": 1,\n" +
                "\"clusters\": null,\n" +
                "\"arguments\": null,\n" +
                "\"fixes\": null,\n" +
                "\"suggests\": null,\n" +
                "\"alternate_url\": \"https://hh.ru/search/vacancy?enable_snippets=true&items_on_page=1&page=1&text=java\"\n" +
                "}"
        assertNotNull(apiV1RequestHHJsonForObject(json))
    }
}
