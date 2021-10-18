package com.example.routing

import com.example.db.DataBase
import com.example.db.getByPhone
import com.example.entity.LoginBody
import com.example.entity.User
import com.example.entity.isNotEmpty
import com.example.entity.toJwtUser
import com.example.jwtConfig
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authRouting() {
    val col = DataBase.user

    route("/signup") {
        post {
            val user = call.receive<User>()

            if (user.isNotEmpty()) {
                if (col.insertOne(user).wasAcknowledged()) {
                    call.respondText("User Created", status = HttpStatusCode.Created)
                } else {
                    call.respondText("User Creation Failed", status = HttpStatusCode.BadRequest)
                }
            } else {
                call.respondText("Insufficient Information", status = HttpStatusCode.BadRequest)
            }
        }
    }

    route("/login") {
        post {
            val loginBody = call.receive<LoginBody>()
            val user = col.getByPhone(loginBody.phone)

            if (user != null && user.password == loginBody.password) {
                val token = jwtConfig.generateToken(user.toJwtUser())
                call.respond(hashMapOf("token" to token))
            } else {
                call.respondText("No Match Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}