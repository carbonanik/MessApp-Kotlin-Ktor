package com.example.entity

import com.example.util.ObjectIDSerializer
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.eclipse.jetty.util.log.Log

@Serializable
data class Post(
    @BsonId
    @Serializable(with = ObjectIDSerializer::class)
    val id: ObjectId = ObjectId(),
    val authorId: String,
    val title: String,
    val body: String,
    val time: Long,
    val privacy: String,
    val contentUrl: String,
    val contentType: String,
)

enum class ContentType {
    IMAGE,
    VIDEO,
    PDF,
    RAW_FILE
}