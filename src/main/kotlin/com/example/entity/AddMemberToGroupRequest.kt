package com.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class AddMemberToGroupRequest(
    val groupId: String,
    val userIds: List<String>,
    val asAdmin: Boolean
)

typealias RemoveMemberFromGroupRequest = AddMemberToGroupRequest
