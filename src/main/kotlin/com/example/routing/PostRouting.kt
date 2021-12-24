package com.example.routing

import com.example.authenticationConfig
import com.example.db.PostCollection
import com.example.entity.CreatePostRequest
import com.example.entity.Post
import com.example.entity.createPost
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.postRouting(postColl: PostCollection) {
    route(HttpRoutes.Post.route) {
        authenticate(authenticationConfig) {
            post(HttpRoutes.Post.CREATE) {
                val post = call.receive<CreatePostRequest>().createPost()
                if (postColl.add(post)) {
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
                val post = postColl.getById(postId) ?: return@get call.respondText(
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

                val posts = postColl.getAllOfUser(userId)
                if (posts.isNotEmpty()) {
                    call.respond(posts)
                } else {
                    call.respondText(
                        "No post of this user found",
                        status = HttpStatusCode.NotFound
                    )
                }
            }

            get("${HttpRoutes.Post.GET_ALL_UNTIL}/{time}") {
                val time = call.parameters["time"] ?: return@get call.respondText(
                    "No time provided",
                    status = HttpStatusCode.NotAcceptable
                )

                val posts = postColl.getAllUntil(time.toLong())

                if (posts.isNotEmpty()) {
                    call.respond(posts)
                } else {
                    call.respondText(
                        "No post found",
                        status = HttpStatusCode.NotFound
                    )
                }
            }

            get("${HttpRoutes.Post.GET_ALL_BETWEEN}/{old_time}{new_time}") {
                val oldTime = call.parameters["old_time"]
                val newTime = call.parameters["new_time"]

                if (oldTime == null || newTime == null) {
                    return@get call.respondText(
                        "Provide all information",
                        status = HttpStatusCode.NotAcceptable
                    )
                }

                val posts = postColl.getAllBetween(oldTime.toLong(), newTime.toLong())
                if (posts.isNotEmpty()){
                    call.respond(posts)
                } else {
                    call.respondText (
                        "No post found",
                        status = HttpStatusCode.NotFound
                            )
                }
            }


            /// authentication
        }
    }
}

