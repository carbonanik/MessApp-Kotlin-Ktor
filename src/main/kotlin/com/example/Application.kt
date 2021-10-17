package com.example

import com.example.db.DB
import com.example.routing.initializeRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt() ) { //todo System.getenv("PORT").toInt()
        install(ContentNegotiation) { json() }
        val db = DB
        initializeRouting()
    }.start(wait = true)
}
