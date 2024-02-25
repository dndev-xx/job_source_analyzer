package org.example.source

import org.example.util.ConfigLoader

data class JobSource(val source: String, var workType: WorkType) {

    companion object {
        val HH = ConfigLoader.getProperty("hh")?.let { JobSource(it, WorkType.STANDARD) }
        val HABR = ConfigLoader.getProperty("habr")?.let { JobSource(it, WorkType.STANDARD) }
    }
}
