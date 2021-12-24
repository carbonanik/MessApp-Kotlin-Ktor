package com.example.routing

import com.example.db.GroupCollection
import com.example.db.MessageCollection
import com.example.db.UserCollections
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.initializeRouting() {
    routing {
        userRouting(UserCollections())
        authRouting(UserCollections())
        socketRoute(MessageCollection())
        groupRouting(GroupCollection())
        fileRouting()
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}