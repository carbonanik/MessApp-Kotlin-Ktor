package com.example.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.types.ObjectId
import java.util.*

object ObjectIDSerializer: KSerializer<ObjectId> {

    override val descriptor = PrimitiveSerialDescriptor("ObjectID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ObjectId {
        return ObjectId(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: ObjectId) {
        encoder.encodeString(value.toString())
    }
}