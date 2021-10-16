package com.example

import com.example.routing.initializeRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        install(ContentNegotiation) { json() }
        initializeRouting()
    }.start(wait = true)
}
