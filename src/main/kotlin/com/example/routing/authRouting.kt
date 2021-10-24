package com.example.routing

import com.example.db.DataBase
import com.example.db.add
import com.example.db.getByPhone
import com.example.entity.*
import com.example.jwtConfig
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authRouting() {
    val userCol = DataBase.user

    route("/signup") {
        post {
            val requestBody = call.receive<AuthRequest>()
            val user = requestBody.createUser()
                ?: return@post call.respondText("Insufficient Information", status = HttpStatusCode.NotAcceptable)

            if (userCol.getByPhone(user.phone) != null) return@post call.respondText("Phone Number exist in database", status = HttpStatusCode.Conflict)

            if (userCol.add(user)) {
                val token = jwtConfig.generateToken(user.toJwtUser())
                call.respond(user.authResponse(token)) }

            else call.respondText("User Creation Failed", status = HttpStatusCode.InternalServerError)
        }
    }

    route("/login") {
        post {
            val authRequest = call.receive<AuthRequest>()
            if (!authRequest.canLogin()) return@post call.respondText("Insufficient Information", status = HttpStatusCode.NotAcceptable)

            val user = userCol.getByPhone(authRequest.phone) ?: return@post call.respondText("No User Found", status = HttpStatusCode.NotFound)
            if ( user.password != authRequest.password) return@post call.respondText("Password Dose Not Match", status = HttpStatusCode.NotFound)

            val token = jwtConfig.generateToken(user.toJwtUser())
            call.respond(user.authResponse(token))
        }
    }
}