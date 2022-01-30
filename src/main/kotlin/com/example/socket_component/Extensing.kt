package com.example.socket_component

import com.example.entity.Message
import com.example.routing.Connection
import com.example.util.messageToJson
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

suspend fun <T> ApplicationCall.respondNotEmptyList(
    list: List<T>?,
    errStr: String = "No Item Found",
    errStatusCode: HttpStatusCode = HttpStatusCode.NotFound
) {
    if (list == null || list.isEmpty()) {
        respondText(errStr, status = errStatusCode)
    } else {
        respond(list)
    }
}

suspend inline fun ApplicationCall.nullRespond(errStr: String = "Null Parameter / Data") {
    return respondText(errStr, status = HttpStatusCode.NotAcceptable)
}
