package jsa.mapper

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import jsa.mapper.api.v1.models.CdvRequest
import jsa.mapper.api.v1.models.CdvResponse

val apiV1Mapper = JsonMapper.builder().run {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
    build()
}!!

fun apiV1RequestSerialize(request: CdvRequest): String = apiV1Mapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : CdvRequest> apiV1RequestDeserialize(json: String): T =
    apiV1Mapper.readValue(json, CdvRequest::class.java) as T
fun apiV1ResponseSerialize(response: CdvResponse): String = apiV1Mapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : CdvResponse> apiV1ResponseDeserialize(json: String): T =
    apiV1Mapper.readValue(json, CdvResponse::class.java) as T