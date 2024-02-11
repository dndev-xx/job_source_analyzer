package org.example.model

@JvmInline
value class JsaLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = JsaLock("")
    }
}