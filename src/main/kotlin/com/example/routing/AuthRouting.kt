package com.example.routing

import com.example.authentication.JwtConfig
import com.example.authentication.getJwtUser
import com.example.authenticationConfig
import com.example.db.UserDataSource
import com.example.entity.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authRouting(userDataSource: UserDataSource, jwtConfig: JwtConfig) {

    route(HttpRoutes.Auth.route) {
        get { call.respondText("Auth Route") }
        /**
         * signup using the provided information
         */
        post(HttpRoutes.Auth.SIGNUP) {
            val authRequest = call.receive<AuthRequest>()

            val user = authRequest.extractUser()
                ?: return@post call.respondText(
                    "Insufficient Information",
                    status = HttpStatusCode.NotAcceptable
                )

            if (userDataSource.existUserByPhone(user.phone))
                return@post call.respondText(
                    "Phone number already exist in database",
                    status = HttpStatusCode.Conflict
                )

            if (userDataSource.add(user)) {
                val token = jwtConfig.generateToken(user.toJwtUser())
                call.respond(user.authResponse(token))
            } else call.respondText(
                "User Creation Failed",
                status = HttpStatusCode.InternalServerError
            )
        }

        /**
         * login using password and phone
         */
        post(HttpRoutes.Auth.LOGIN) {
            val authRequest = call.receive<AuthRequest>()
            if (!authRequest.canLogin()) return@post call.respondText(
                "Insufficient Information",
                status = HttpStatusCode.NotAcceptable
            )

            val user = userDataSource.getByPhone(authRequest.phone) ?: return@post call.respondText(
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
            /**
             * refresh the auth token
             */
            get(HttpRoutes.Auth.REFRESH) {

                //fixme retrieve user from database
                val jwtUser = call.getJwtUser()
                val token = jwtConfig.generateToken(jwtUser)
                call.respond(jwtUser.authResponse(token))
            }
        }
    }
}