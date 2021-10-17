package com.example.routing

import com.example.User
import com.example.db.DB
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.json

fun Route.authRouting(){
    val col = DB.user
    route("/signup"){
        post {
            try {
                val user = call.receive<User>()
                call.respond(user)
            } catch (e: Exception){
                call.respond(e.json)
            }
//            if (col.insertOne(user).wasAcknowledged()){
//                call.respondText("User Successfully Created", status = HttpStatusCode.Created)
//            } else {
//                call.respondText("User Creation Failed", status = HttpStatusCode.BadRequest)
//            }
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