package com.example.routing

import com.example.authenticationConfig
import com.example.db.PostDataSource
import com.example.entity.CreatePostRequest
import com.example.entity.createPost
import com.example.util.respondNotEmptyList
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.postRouting(postData: PostDataSource) {
    route(HttpRoutes.Post.route) {
        authenticate(authenticationConfig) {
            post(HttpRoutes.Post.CREATE) {
                val post = call.receive<CreatePostRequest>().createPost()
                if (postData.add(post)) {
                    call.respond(post)
                } else {
                    call.respondText(
                        "Internal server error",
                        status = HttpStatusCode.InternalServerError
                    )
                }
            }

            get("${HttpRoutes.Post.GET_BY_ID}/{id}") {
                val postId = call.parameters["id"] ?: return@get call.respondText(
                    "No id provided",
                    status = HttpStatusCode.NotAcceptable
                )
                val post = postData.getById(postId) ?: return@get call.respondText(
                    "Post Not Found Or Internal Error",
                    status = HttpStatusCode.NotFound
                )
                call.respond(post)
            }

            get("${HttpRoutes.Post.GET_OF_USER}/{id}") {
                val userId = call.parameters["id"] ?: return@get call.respondText(
                    "No id provided",
                    status = HttpStatusCode.NotAcceptable
                )

                val posts = postData.getAllOfUser(userId)
                call.respondNotEmptyList(posts, "No post of this user found")
            }

            get("${HttpRoutes.Post.GET_ALL_UNTIL}/{time}") {
                val time = call.parameters["time"] ?: return@get call.respondText(
                    "No time provided",
                    status = HttpStatusCode.NotAcceptable
                )

                val posts = postData.getAllUntil(time.toLong())

                call.respondNotEmptyList(posts, "No post found")

            }

            get(HttpRoutes.Post.GET_ALL_BETWEEN) {
                val oldTime = call.request.queryParameters["old_time"]
                val newTime = call.request.queryParameters["new_time"]

                if (oldTime == null || newTime == null) {
                    return@get call.respondText(
                        "Provide all information",
                        status = HttpStatusCode.NotAcceptable
                    )
                }

                val posts = postData.getAllBetween(oldTime.toLong(), newTime.toLong())
                call.respondNotEmptyList(posts, "No post found")
            }

            // todo not implemented
            get("${HttpRoutes.Post.GET_ALL_PAGED}/{page}") {
                val pageNo = call.parameters["page"]?.toIntOrNull()
                call.respondText("Not Implemented", status = HttpStatusCode.NotImplemented)
            }

            delete("${HttpRoutes.Post.DELETE}/{id}"){
                val id = call.parameters["id"]?: return@delete call.respondText(
                    "No id provided",
                    status = HttpStatusCode.NotAcceptable
                )
                if (postData.delete(id)) call.respondText("Post Successfully Removed", status = HttpStatusCode.Accepted)
                else call.respondText("Can't Removed Post", status = HttpStatusCode.NotFound)
            }
        }
    }
}

