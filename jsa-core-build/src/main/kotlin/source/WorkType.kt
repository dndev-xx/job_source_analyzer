package org.example.source

data class WorkType(val type: String, val duration: Int) {
    companion object {
        val LOW = WorkType("low", 24)
        val STANDARD = WorkType("standard", 5)
        val HIGH = WorkType("high", 2)
    }
}