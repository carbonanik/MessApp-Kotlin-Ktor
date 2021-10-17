package com.example.routing

import com.example.User
import com.example.db.DB
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authRouting(){
    val col = DB.user
    route("/signup"){
        post {
            val authRequest = call.receive<AuthRequest>()
            if (col.insertOne(authRequest.user).wasAcknowledged()){
                call.respondText("User Successfully Created", status = HttpStatusCode.Created)
            } else {
                call.respondText("User Creation Failed", status = HttpStatusCode.BadRequest)
            }
        }
    }

    route("/login"){
        post {
            val authRequest = call.receive<AuthRequest>()
            if (col.insertOne(authRequest.user).wasAcknowledged()){
                call.respondText("User stored correctly", status = HttpStatusCode.Created)
            } else {
                call.respondText("User not correctly stored", status = HttpStatusCode.BadRequest)
            }
        }
    }
}