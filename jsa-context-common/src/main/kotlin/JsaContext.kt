package org.example

import jsa.mapper.api.v1.models.CdvRequest
import jsa.mapper.api.v1.models.CdvResponse
import kotlinx.datetime.Instant
import org.example.const.NONE
import org.example.model.JsaCommand
import org.example.model.JsaError
import org.example.model.JsaLock
import org.example.model.JsaState
import org.example.model.JsaWorkMode

data class JsaContext(
    var command: JsaCommand = JsaCommand.NONE,
    var state: JsaState = JsaState.NONE,
    val errors: MutableList<JsaError> = mutableListOf(),
    var workMode: JsaWorkMode = JsaWorkMode.STUB,
    var timeStart: Instant = Instant.NONE,
    var lock: JsaLock = JsaLock.NONE,

    val request: CdvRequest?,
    val response: CdvResponse?
)