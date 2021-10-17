package com.example.routing

import com.example.User
import com.example.db.DB
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.`in`
import org.litote.kmongo.eq

fun Route.userRouting() {
    val col = DB.user
    route("/user") {
        get {
            val list = col.find().toList()
            if (list.isNotEmpty()) {
                call.respond(list)
            } else {
                call.respondText("No user found", status = HttpStatusCode.NotFound)
            }
        }

        get("/by-name/{name}") {
            val name = call.parameters["name"]
                ?: return@get call.respondText(
                    "No Name Provided Or Bad Request",
                    status = HttpStatusCode.BadRequest
                )
            val user = DB.user.findOne(User::name eq name)
                ?: return@get call.respondText(
                    "No user with name $name",
                    status = HttpStatusCode.NotFound
                )
            call.respond(user)
        }

        post("/by-phone") {
            val numbers = call.receive<List<String>>()
            val users = col.find(User::phone `in` numbers).toList()
            call.respond(users)
        }

        delete("{id}") {
//            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
//            if (userStorage.removeIf { it.id == id }) {
//                call.respondText("User removed correctly", status = HttpStatusCode.Accepted)
//            } else {
//                call.respondText("Not Found", status = HttpStatusCode.NotFound)
//            }
        }
    }
}