package org.example.source

data class JobSource(val source: String, var workType: WorkType) {

    companion object {
        val HH = JobSource("https://api.hh.ru/vacancies?", WorkType.STANDARD)
        val HABR = JobSource("https://career.habr.com/vacancies?", WorkType.STANDARD)
    }
}
