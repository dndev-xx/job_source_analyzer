package org.example.grabber

import jsa.mapper.apiV1RequestHHJsonForObject
import jsa.mapper.model.HHModelApi
import jsa.mapper.model.HabrModelApi
import jsa.mapper.model.ItemHabr
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException

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

fun fetchDataFromSourceHABR(source: String): HabrModelApi {
    val rsl = HabrModelApi(ArrayList())
    val connection: Connection = Jsoup.connect(source) // "https://career.habr.com/vacancies?page=1&q=java&type=all"
    val document: Document = connection.get()
    val descriptionElements: Elements? = document.getElementsByAttributeValue("class", "vacancy-card__inner")
    descriptionElements!!.forEach {getSourceElementFromHabr(it).let { item -> rsl.items.add(item)}
    }
    return rsl
}
private fun getSourceElementFromHabr(elem: Element): ItemHabr {
    val title: Element? = elem.selectFirst(".vacancy-card__title")
    val company: Element? = elem.selectFirst(".vacancy-card__company")
    val companyTitle: Element? = elem.selectFirst(".vacancy-card__company-title")
    val salary: Element? = elem.selectFirst(".vacancy-card__salary")
    val meta: Element? = elem.selectFirst(".vacancy-card__meta")
    val link = title!!.child(0)
    val linkVacancy: String = "https://career.habr.com" + link.attr("href")
    val linkCompany: String = "https://career.habr.com" + companyTitle!!.child(0).attr("href")
    val dataElement: Element = elem.selectFirst(".vacancy-card__date")!!
    val dataLink = dataElement.child(0)
    val descriptionVacancy: String = retrieveDescription(linkVacancy)
    val item = ItemHabr(
        company!!.text(),
        title.text(),
        salary!!.text(),
        descriptionVacancy,
        dataLink.text().toString(),
        meta!!.text(),
        linkVacancy,
        linkCompany
    )
    return item
}
@Throws(IOException::class)
private fun retrieveDescription(link: String): String {
    val connection = Jsoup.connect(link)
    val document = connection.get()
    val descriptionElement = document.selectFirst(".vacancy-description__text")!!
    return extractVacancyInfo(descriptionElement)
}

private fun extractVacancyInfo(html: Element): String {
    val vacancyInfo: StringBuilder = StringBuilder()
    val companyAndTeamSection: Element? = html.selectFirst("div.vacancy-description__text > h3:contains(О компании и команде) + div.style-ugc")
    vacancyInfo.append("О компании и команде:\n")
    companyAndTeamSection!!.select("p").forEach { p -> vacancyInfo.append(p.text()).append("\n") }

    val candidateExpectationsSection: Element? = html.selectFirst("div.vacancy-description__text > h3:contains(Ожидания от кандидата) + div.style-ugc")
    vacancyInfo.append("\n\nОжидания от кандидата:\n")
    candidateExpectationsSection!!.select("li").forEach { li -> vacancyInfo.append("\u2022 ").append(li.text()).append("\n") }

    val jobConditionsSection: Element? = html.selectFirst("div.vacancy-description__text > h3:contains(Условия работы) + div.style-ugc")
    vacancyInfo.append("\n\nУсловия работы:\n")
    jobConditionsSection!!.select("li").forEach { li -> vacancyInfo.append("\u2022 ").append(li.text()).append("\n") }
    return vacancyInfo.toString()
}

private fun isValidSourceFormat(source: String): Boolean {
    val regex = Regex("https://api.hh.ru/vacancies\\?text=\\w+&page=\\d+&per_page=\\d+")
    return regex.matches(source)
}