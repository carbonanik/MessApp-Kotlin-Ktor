package com.example.util

import com.example.entity.Message

class MessageDigest (
    message: Message,
){

}

fun sentStatus(localId: String): Message.MessageStatusCarrier {
    return Message.MessageStatusCarrier(
        messageLocalId = localId,
        time = System.currentTimeMillis(),
        status = "SENT",
    )
}