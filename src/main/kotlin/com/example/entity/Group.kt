package com.example.entity

import com.example.util.ObjectIDSerializer
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

@Serializable
data class Group(
    @BsonId
    @Serializable(with = ObjectIDSerializer::class)
    val id: ObjectId = ObjectId(),
    val name: String,
    val admin: List<String>,
    val members: List<String>
)
