package org.example.exec

import jsa.mapper.apiV1RequestHHJsonForObject
import jsa.mapper.model.HHModelApi
import okhttp3.OkHttpClient
import okhttp3.Request

fun fetchDataFromSourceHH(source: String): HHModelApi? {
    if (isValidSourceFormat(source)) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(source)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                return responseBody?.let { apiV1RequestHHJsonForObject(it) }
            } else {
                println("Request failed: ${response.code}")
            }
        }
    } else {
        println("Invalid source format. Source must match the pattern: https://api.hh.ru/vacancies?text=java&page=1&per_page=1")
    }
    return null
}

fun isValidSourceFormat(source: String): Boolean {
    val regex = Regex("https://api.hh.ru/vacancies\\?text=\\w+&page=\\d+&per_page=\\d+")
    return regex.matches(source)
}