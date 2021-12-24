package com.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class CreateGroupRequest(
    val name: String,
    val admin: List<String>,
    val members: List<String>
)

fun CreateGroupRequest.extractGroup(): Group? {
    if (!hasRequiredData()) return null
    return Group(name = name, admin = admin, members = members)
}

fun CreateGroupRequest.hasRequiredData(): Boolean {
    return name.isNotEmpty() && admin.isNotEmpty() && members.isNotEmpty()
}


