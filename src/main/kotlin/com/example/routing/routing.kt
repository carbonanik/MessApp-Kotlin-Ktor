package com.example.routing

import com.example.db.GroupCollection
import com.example.db.MessageCollection
import com.example.db.UserCollections
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.initializeRouting() {
    routing {
        val userCollections = UserCollections()
        userRouting(userCollections)
        authRouting(userCollections)
        socketRoute(MessageCollection(), userCollections)
        groupRouting(GroupCollection())
        fileRouting()
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}