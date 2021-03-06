package com.example.entity

import com.example.util.ObjectIDSerializer
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Post(
    @BsonId
    @Serializable(with = ObjectIDSerializer::class)
    val id: ObjectId = ObjectId(),
    val authorId: String,
    val title: String? = null,
    val body: String,
    val time: Long,
    val privacy: String,
    val contentUrl: String? = null,
    val contentType: String? = null,
)

enum class ContentType {
    IMAGE,
    VIDEO,
    PDF,
    RAW_FILE,
    UNSUPPORTED,
}