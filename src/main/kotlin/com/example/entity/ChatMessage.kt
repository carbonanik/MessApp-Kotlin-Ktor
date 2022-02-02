package com.example.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.*

data class ChatMessage(
    @BsonId
    val id: ObjectId,
    val timestamp: Long,
    val text: String? = null,
    val mediaUrl: String? = null,
    val mediaType: String? = null,

    val sender: User? = null,
    val receiver: User? = null,
    val group: Group? = null,

    val senderId: ObjectId? = null,
    val receiverId: ObjectId? = null,
    val groupId: ObjectId? = null,
)

fun Message.TextMessage.toChatMessage(): ChatMessage {

    return ChatMessage(
        id = ObjectId(id),
//        localId = localId,
        timestamp = timestamp,
        text = text,
        mediaUrl = mediaUrl,

        senderId = (this as Message).sender?.id,
        receiverId = (this as Message).receiver?.id,
        groupId = (this as Message).group?.id,

        sender = (this as Message).sender,
        receiver = (this as Message).receiver,
        group = (this as Message).group
    )
}

fun ChatMessage.toMessage(): Message {
    return Message.TextMessage(
        id = id.toString(),
//        localId = localId,
        timestamp = timestamp,
        text = text,
        mediaUrl = mediaUrl
    ).apply {
        this.sender = this@toMessage.sender
        this.receiver = this@toMessage.receiver
        this.group = this@toMessage.group
    }
}

enum class MessageStatus(val value: String) {
    SENT("SENT"),
    DELIVERED("DELIVERED"),
    SEEN("SEEN")
}