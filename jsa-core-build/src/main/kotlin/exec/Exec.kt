package org.example.exec

import jsa.mapper.api.v1.models.CdvResponse
import jsa.mapper.api.v1.models.CdvStartingResponse
import jsa.mapper.apiV1ResponseDeserialize
import okhttp3.OkHttpClient
import okhttp3.Request

//TODO request and mapping json to object

suspend fun fetchDataFromSource(source: String): CdvResponse {
    val client = OkHttpClient()
    var rsl = CdvStartingResponse()
    val request = Request.Builder()
        .url(source)
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            println(responseBody)
            rsl = responseBody?.let { apiV1ResponseDeserialize<CdvResponse>(it) as CdvStartingResponse }!!
            println("Response: $rsl")
        } else {
            println("Request failed: ${response.code}")
        }
    }
    return rsl
}

suspend fun main() {
    fetchDataFromSource("https://api.hh.ru/vacancies?java&page=1&per_page=1")
}