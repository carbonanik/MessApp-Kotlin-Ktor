package com.example.routing

import com.example.authentication.JwtConfig
import com.example.authenticationConfig
import com.example.db.MessageCollection
import com.example.db.UserCollections
import com.example.entity.toMessage
import com.example.socket_component.respondNotEmptyList
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.bson.types.ObjectId

fun Route.messageRouting(messageCollection: MessageCollection, userCollections: UserCollections) {
    route("/message") {
        authenticate(authenticationConfig) {
            get("/get-all-by-receiver") {
                val user =
                    call.authentication.principal as JwtConfig.JwtUser

                val message = messageCollection.getAllByReceiver(ObjectId(user.id))
                    .map { it.toMessage() }

                call.respondNotEmptyList(message, "No message found")
            }

            get("/unread") {
                val user =
                    call.authentication.principal as JwtConfig.JwtUser

                val userFromDB = userCollections.getByID(user.id)

                val unreadMessage = userFromDB?.unreadMessage?.mapNotNull {
                    messageCollection.getById(it)?.toMessage()
                }

                call.respondNotEmptyList(unreadMessage, "No Unread Message Found")
            }
        }
    }
}