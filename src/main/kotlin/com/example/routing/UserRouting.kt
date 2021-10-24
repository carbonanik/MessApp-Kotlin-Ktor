package com.example.routing

import com.example.authentication.JwtConfig
import com.example.db.*
import com.example.entity.toUser
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRouting() {
    route("/user") {
        authenticate("auth-jwt") {
            val userCol = DataBase.user

            /**
             * testing purpose only
             */
            get("/me") {
                val jwtUser = call.authentication.principal as JwtConfig.JwtUser
                call.respond(jwtUser.toUser())
            }

            /**
             * get total user list
             * it will be removed
             */
            get("/list") {
                val list = userCol.getAll()
                if (list.isNotEmpty()) call.respond(list)
                else call.respondText("No user found", status = HttpStatusCode.NotFound)
            }

            /**
             * get user by his name
             */
            get("/by-name/{name}") {
                val name = call.parameters["name"]
                    ?: return@get call.respondText("No Name Provided Or Bad Request", status = HttpStatusCode.BadRequest)
                val user = userCol.getByName(name)
                    ?: return@get call.respondText("No user with name $name", status = HttpStatusCode.NotFound)
                call.respond(user)
            }

            /**
             * get user by his name
             */
            get("/by-id/{id}") {
                val id = call.parameters["id"]
                    ?: return@get call.respondText("No ID Provided Or Bad Request", status = HttpStatusCode.BadRequest)
                val user = userCol.getByID(id)
                    ?: return@get call.respondText("No user with ID $id", status = HttpStatusCode.NotFound)
                call.respond(user)
            }

            /**
             * get list of user that match the number list provided by user
             */
            post("/by-phone") {
                val numbers = call.receive<List<String>>()
                val users = userCol.getByPhones(numbers)

                if (users.isNotEmpty()) call.respond(users)
                else call.respondText("No User Found", status = HttpStatusCode.NotFound)
            }

            delete("/{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

                if (userCol.delete(id)) call.respondText("User Successfully Removed", status = HttpStatusCode.Accepted)
                else call.respondText("Can't Removed User", status = HttpStatusCode.NotFound)
            }
        }
    }
}