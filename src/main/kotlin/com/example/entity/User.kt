package com.example.entity

import com.example.authentication.JwtConfig
import com.example.util.ObjectIDSerializer
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    @BsonId
    @Serializable(with = ObjectIDSerializer::class)
    val id: ObjectId = ObjectId(),
    val name: String,
    val phone: String,
    val password: String,
    val email: String? = null,
    val address: String? = null
)

fun User.isNotEmpty() = name.isNotEmpty()
        && phone.isNotEmpty()
        && password.isNotEmpty()

fun User.toJwtUser() = JwtConfig.JwtUser(phone = phone, name = name)