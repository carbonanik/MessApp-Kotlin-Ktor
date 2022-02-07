package com.example.routing

import com.example.authenticationConfig
import com.example.db.GroupDataSource
import com.example.entity.*
import com.example.util.respondNotEmptyList
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.groupRouting(groupData: GroupDataSource) {
    route(HttpRoutes.Group.route) {
        authenticate(authenticationConfig) {

            /**
             * create new group
             */
            post(HttpRoutes.Group.CREATE) {
                val createGroupRequest = call.receive<CreateGroupRequest>()
                val group = createGroupRequest.extractGroup() ?: return@post call.respondText(
                    "Required data is not provided",
                    status = HttpStatusCode.NotAcceptable
                )

                if (groupData.add(group)) call.respond(group)
                else call.respondText(
                    "Group Creation Failed",
                    status = HttpStatusCode.InternalServerError
                )
            }

            /**
             * get group by id
             */
            get("${HttpRoutes.Group.GET_BY_ID}/{id}") {
                val id = call.parameters["id"]
                    ?: return@get call.respondText("No ID Provided Or Bad Request", status = HttpStatusCode.BadRequest)
                val group = groupData.getById(id)
                    ?: return@get call.respondText("No group with ID $id", status = HttpStatusCode.NotFound)
                call.respond(group)
            }

            /**
             * add member to group
             */
            post(HttpRoutes.Group.ADD_MEMBER) {
                val addMemberRequest = call.receive<AddMemberToGroupRequest>()

                val newGroup: Group = if (addMemberRequest.asAdmin) {
                    groupData.addAdmin(addMemberRequest.groupId, addMemberRequest.userIds[0])
                        ?: return@post call.respondText(
                            "Unable to add admin to group",
                            status = HttpStatusCode.InternalServerError
                        )
                } else {
                    groupData.addMember(addMemberRequest.groupId, addMemberRequest.userIds)
                        ?: return@post call.respondText(
                            "Unable to add member to group",
                            status = HttpStatusCode.InternalServerError
                        )
                }
                call.respond(newGroup)
            }

            /**
             * remove member from group
             */
            post(HttpRoutes.Group.REMOVE_MEMBER) {
                val removeMember = call.receive<RemoveMemberFromGroupRequest>()

                val newGroup: Group = if (removeMember.asAdmin) {
                    groupData.removeAdmin(removeMember.groupId, removeMember.userIds[0])
                        ?: return@post call.respondText(
                            "Unable to add admin to group",
                            status = HttpStatusCode.InternalServerError
                        )
                } else {
                    groupData.removeMember(removeMember.groupId, removeMember.userIds)
                        ?: return@post call.respondText(
                            "Unable to add member to group",
                            status = HttpStatusCode.InternalServerError
                        )
                }
                call.respond(newGroup)
            }

            /**
             * get all group the user member of
             */
            get("${HttpRoutes.Group.ALL_FOR_USER}/{id}") {
                val id = call.parameters["id"]
                    ?: return@get call.respondText(
                        "No ID Provided",
                        status = HttpStatusCode.BadRequest
                    )

                val groupList = groupData.getByUser(id)

                call.respondNotEmptyList(groupList, "Does not have any group")
            }
        }
    }
}