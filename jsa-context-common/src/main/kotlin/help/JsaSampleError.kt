package org.example.help

import org.example.model.JsaError

fun Throwable.asJsaError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = JsaError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)