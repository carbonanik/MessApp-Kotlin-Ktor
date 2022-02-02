package com.example.routing

import com.example.authentication.JwtConfig
import com.example.authenticationConfig
import com.example.db.MessageDataSource
import com.example.db.UserDataSource
import com.example.entity.toMessage
import com.example.util.respondNotEmptyList
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.bson.types.ObjectId

fun Route.messageRouting(messageDataSource: MessageDataSource, userDataSource: UserDataSource) {
    route("/message") {
        authenticate(authenticationConfig) {
            get("/get-all-by-receiver") {
                val user =
                    call.authentication.principal as JwtConfig.JwtUser

                val message = messageDataSource.getAllByReceiver(ObjectId(user.id))
                    .map { it.toMessage() }

                call.respondNotEmptyList(message, "No message found")
            }

            get("/unread") {
                val user =
                    call.authentication.principal as JwtConfig.JwtUser

                val userFromDB = userDataSource.getByID(user.id)

                val unreadMessage = userFromDB?.unreadMessage?.mapNotNull {
                    messageDataSource.getById(it)?.toMessage()
                }

                call.respondNotEmptyList(unreadMessage, "No Unread Message Found")
            }
        }
    }
}