package com.example.routing

import com.example.authentication.getJwtUser
import com.example.authenticationConfig
import com.example.db.MessageDataSource
import com.example.db.UserDataSource
import com.example.entity.toMessage
import com.example.util.SocketButterflies
import com.example.util.respondNotEmptyList
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.messageRouting(
    messageData: MessageDataSource,
    userDataSource: UserDataSource,
    butterflies: SocketButterflies
) {
    route(HttpRoutes.Message.route) {
        authenticate(authenticationConfig) {

            //
            get(HttpRoutes.Message.GET_ALL_BY_RECEIVER) {
                val user = call.getJwtUser()

                val message = messageData.getAllByReceiver(user.id)
                    .map { it.toMessage() }

                call.respondNotEmptyList(message, "No message found")
            }

            /**
             * get all unread message and remove unread list from user
             */
            // TODO: 2/5/2022 Send all sender 'send status' message
            // Not In user Currently
            get(HttpRoutes.Message.UNREAD_FOR_USER) {
                val user = call.getJwtUser()

                val userFromDB = userDataSource.getByID(user.id) ?: return@get call
                    .respondText(
                        "User Not Found In Database",
                        status = HttpStatusCode.NotFound
                    )

                val unreadMessage = messageData.getByIds(userFromDB.unreadMessage)


                try {
                    call.respondNotEmptyList(unreadMessage.map { it.toMessage() }, "No Unread Message Found")
                } catch (e: Exception) {
                    throw e
                } finally {
                    userDataSource.removeAllUnreadMessage(user.id, userFromDB.unreadMessage)
                    unreadMessage.forEach {
                        butterflies.sendSendStatus(it.senderId!!)
                    }
                }
            }
        }
    }
}