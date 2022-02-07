package com.example.routing

import com.example.authentication.getJwtUser
import com.example.authenticationConfig
import com.example.db.UserDataSource
import com.example.entity.toUser
import com.example.util.respondNotEmptyList
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRouting(userData: UserDataSource) {
    route(HttpRoutes.User.route) {
        authenticate(authenticationConfig) {

            /**
             * me / logged i user
             */
            get(HttpRoutes.User.ME) {
                val jwtUser = call.getJwtUser()
                call.respond(jwtUser.toUser())
            }

            /**
             * get total user list
             * it will be removed
             */
            get(HttpRoutes.User.GET_ALL) {
                val list = userData.getAll()
                if (list.isNotEmpty()) call.respond(list)
                else call.respondText("No user found", status = HttpStatusCode.NotFound)
            }

            /**
             * get user by his name
             */
            // FIXME: 2/3/2022 fix case sensitivity
            get("${HttpRoutes.User.GET_BY_NAME}/{name}") {
                val name = call.parameters["name"]
                    ?: return@get call.respondText(
                        "No Name Provided Or Bad Request",
                        status = HttpStatusCode.BadRequest
                    )

                val user = userData.queryByName(name)

                call.respondNotEmptyList(user, "No user with name $name")
            }

            /**
             * get user by id
             */
            // FIXME: 2/3/2022 Invalid hex for object id return 200 OK
            get("${HttpRoutes.User.GET_BY_ID}/{id}") {
                val id = call.parameters["id"]
                    ?: return@get call.respondText("No ID Provided Or Bad Request", status = HttpStatusCode.BadRequest)
                val user = userData.getByID(id)
                    ?: return@get call.respondText("No user with ID $id", status = HttpStatusCode.NotFound)
                call.respond(user)
            }

            /**
             * get list of user that match the number list provided by user
             */
            post(HttpRoutes.User.GET_BY_PHONE) {
                val numbers = call.receive<List<String>>()
                val users = userData.getByPhones(numbers)

                call.respondNotEmptyList(users, "No User Found")
            }

            /**
             * delete user by user id
             */
            delete("${HttpRoutes.User.DELETE}/{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

                if (userData.delete(id)) call.respondText("User Successfully Removed", status = HttpStatusCode.Accepted)
                else call.respondText("Can't Removed User", status = HttpStatusCode.NotFound)
            }
        }
    }
}