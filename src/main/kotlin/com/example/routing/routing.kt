package com.example.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get


fun Application.initializeRouting() {
    routing {
        userRouting(get())
        authRouting(get(), get())
        socketRoute(get())
        messageRouting(get(), get(), get())
        groupRouting(get())
        postRouting(get())
        fileRouting()
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}