package com.example

import com.example.util.ObjectIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


@Serializable
data class User (
    @BsonId
    @Serializable(with = ObjectIDSerializer::class)
    val id: ObjectId = ObjectId(),
    val name: String,
    val phone: String? = null,
    val email: String? = null,
    val address: String? = null
)