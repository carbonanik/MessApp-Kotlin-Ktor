package com.example.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
sealed class Message {

    var sender: User? = null
    var receiver: User? = null
    var group: Group? = null

    @Serializable
    @SerialName("TextMessage")
    data class TextMessage(
        val id: String,
        val timestamp: Long,
        val text: String? = null,
        val mediaUrl: String? = null,
        val mediaType: String? = null,
    ) : Message()


    @Serializable
    @SerialName("MessageStatusCarrier")
    data class MessageStatusCarrier(
        val messageId: String,
        val time: Long,
        val status: String
    ) : Message()


    @Serializable
    @SerialName("WanderingStatus")
    data class WanderingStatus(
        val status: String,
    ) : Message()


    @Serializable
    sealed class RTCMessage : Message() {

        @Serializable
        @SerialName("RtcRequest")
        data class RtcRequest(
            val rtcConType: String,
            val rtcReqType: String,
        ) : RTCMessage()


        @Serializable
        @SerialName("SDPMessage")
        data class SDPMessage(
            val sdpType: String,
            val sdp: String,
        ) : RTCMessage()


        @Serializable
        @SerialName("ICEMessage")
        data class ICEMessage(
            val serverUrl: String,
            val sdpMid: String,
            val sdpMLineIndex: Int,
            val sdpCandidate: String,
            val candidateType: String
        ) : RTCMessage()


        @Serializable
        @SerialName("PeerLeft")
        data class PeerLeft(
            val reason: String
        ) : RTCMessage()

    }
}