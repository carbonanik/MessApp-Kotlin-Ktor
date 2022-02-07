package com.example.util

import com.example.entity.Message
import com.example.entity.Connection
import com.example.serialization.messageToJson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*


suspend fun Connection.sendMessage(message: Message) {
    session.send(message.messageToJson())
}

suspend fun DefaultWebSocketSession.sendMessage(message: Message) {
    send(message.messageToJson())
}

suspend fun ApplicationCall.respondNotEmptyList(
    list: List<Any>?,
    errStr: String = "No Item Found",
    errStatusCode: HttpStatusCode = HttpStatusCode.NotFound
) {
    if (list == null || list.isEmpty()) {
        respondText(errStr, status = errStatusCode)
    } else {
        respond(list)
    }
}

suspend fun ApplicationCall.nullRespond(
    errStr: String = "Null Parameter / Data",
    status: HttpStatusCode = HttpStatusCode.NotAcceptable
) {
    respondText(errStr, status = status)
}
