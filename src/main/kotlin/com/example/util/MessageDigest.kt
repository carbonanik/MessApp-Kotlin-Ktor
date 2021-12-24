package com.example.util

import com.example.entity.Message

fun messageDigest(
    message: Message,
){
    when(message){
        is Message.MessageStatusCarrier -> {}
        is Message.RTCMessage.ICEMessage -> {}
        is Message.RTCMessage.PeerLeft -> {}
        is Message.RTCMessage.RtcRequest -> {}
        is Message.RTCMessage.SDPMessage -> {}
        is Message.TextMessage -> {}
        is Message.WanderingStatus -> {}
    }
}

fun createSentMessageStatus(localId: String): Message.MessageStatusCarrier {
    return Message.MessageStatusCarrier(
        messageLocalId = localId,
        time = System.currentTimeMillis(),
        status = "SENT",
    )
}