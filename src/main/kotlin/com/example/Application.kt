package com.example

import com.example.db.DB
import com.example.routing.initializeRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.litote.kmongo.json

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt() ) { //todo System.getenv("PORT").toInt()
        install(ContentNegotiation) { json() }
        val db = DB
        initializeRouting()
        install(StatusPages){
            exception<Throwable> { cause ->
                call.respond(cause.json)
            }
        }
    }.start(wait = true)
}
