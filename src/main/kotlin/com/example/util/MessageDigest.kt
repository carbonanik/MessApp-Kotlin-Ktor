package com.example.util

import com.example.entity.Message

fun sentStatus(localId: String): Message.MessageStatusCarrier {
    return Message.MessageStatusCarrier(
        messageId = localId,
        time = System.currentTimeMillis(),
        status = "SENT",
    )
}