package org.example.help

import org.example.JsaContext
import org.example.model.JsaCommand

fun JsaContext.isUpdatableCommand() =
    this.command in listOf(JsaCommand.SEARCHING)