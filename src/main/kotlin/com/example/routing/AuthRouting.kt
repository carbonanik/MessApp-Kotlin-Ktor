package com.example.routing

import com.auth0.jwt.JWT
import com.example.authentication.JwtConfig
import com.example.authenticationConfig
import com.example.db.DataBase
import com.example.db.add
import com.example.db.getByPhone
import com.example.entity.*
import com.example.jwtConfig
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authRouting() {
    val userCol = DataBase.user

    route("/auth") {
        post("/signup") {
            val requestBody = call.receive<AuthRequest>()
            val user = requestBody.createUser()
                ?: return@post call.respondText("Insufficient Information", status = HttpStatusCode.NotAcceptable)

            if (userCol.getByPhone(user.phone) != null) return@post call.respondText(
                "Phone Number exist in database",
                status = HttpStatusCode.Conflict
            )

            if (userCol.add(user)) {
                val token = jwtConfig.generateToken(user.toJwtUser())
                call.respond(user.authResponse(token))
            } else call.respondText("User Creation Failed", status = HttpStatusCode.InternalServerError)
        }

        post("/login") {
            val authRequest = call.receive<AuthRequest>()
            if (!authRequest.canLogin()) return@post call.respondText(
                "Insufficient Information",
                status = HttpStatusCode.NotAcceptable
            )

            val user = userCol.getByPhone(authRequest.phone) ?: return@post call.respondText(
                "No User Found",
                status = HttpStatusCode.NotFound
            )
            if (user.password != authRequest.password) return@post call.respondText(
                "Password Dose Not Match",
                status = HttpStatusCode.NotFound
            )

            val token = jwtConfig.generateToken(user.toJwtUser())
            call.respond(user.authResponse(token))
        }

        authenticate(authenticationConfig) {
            get("/refresh-auth") {
                val jwtUser = call.authentication.principal as JwtConfig.JwtUser
                val token = jwtConfig.generateToken(jwtUser)
                println(2.628e+9.toLong())
                call.respond(jwtUser.authResponse(token))
            }
        }
    }
}