package com.example.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.initializeRouting(){
    routing {
        userRouting()
        authRouting()
        socketRoute()
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}