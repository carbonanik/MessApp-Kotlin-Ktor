package com.example.db

import com.example.entity.ChatMessage
import com.example.entity.User
import org.bson.types.ObjectId
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.div
import org.litote.kmongo.eq
import org.litote.kmongo.or

class MessageDataSource(private val messageColl: CoroutineCollection<ChatMessage>) {

    suspend fun add(message: ChatMessage): String? =
        messageColl.insertOne(message).insertedId?.asObjectId()?.value?.toString()

    suspend fun getById(messageId: String) = messageColl
        .findOneById(ObjectId(messageId))

    suspend fun getByIds(ids: List<String>): List<ChatMessage> {
        val oIds = ids.map { ObjectId(it) }
        return messageColl.find(ChatMessage::id `in` oIds).toList()
    }

    suspend fun getAllBySender(userId: String) =
        messageColl.find(ChatMessage::senderId eq userId).toList()

    suspend fun getAllByReceiver(userId: String) =
        messageColl.find(ChatMessage::receiverId eq userId).toList()

    suspend fun getAllByGroup(groupId: String) = messageColl
        .find(ChatMessage::groupId eq groupId).toList()

    suspend fun getAllById(id: String) {
        messageColl.find(
            or(
                ChatMessage::receiver / User::id eq ObjectId(id),
                ChatMessage::senderId eq id,
                ChatMessage::groupId eq id
            )
        )
    }
}
