package com.example.entity

import com.example.authentication.JwtConfig
import com.example.util.ObjectIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
@SerialName("User")
data class User(
    @BsonId
    @Serializable(with = ObjectIDSerializer::class)
    val id: ObjectId = ObjectId(),
    val name: String,
    val phone: String,
    @Transient
    val password: String? = null,
)

fun User.isNotEmpty() = name.isNotEmpty()
        && phone.isNotEmpty()
        && !password.isNullOrEmpty()

fun User.toJwtUser() =
    JwtConfig.JwtUser(id.toString(), phone, name)

fun JwtConfig.JwtUser.toUser() =
    User(id = ObjectId(id), name, phone)