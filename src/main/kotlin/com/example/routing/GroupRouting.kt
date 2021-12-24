package com.example.routing

import com.example.authenticationConfig
import com.example.db.GroupCollection
import com.example.entity.AddMemberToGroupRequest
import com.example.entity.CreateGroupRequest
import com.example.entity.extractGroup
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.groupRouting(groupColl: GroupCollection) {
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

                if (groupColl.add(group)) call.respond(group)
                else call.respondText(
                    "Group Creation Failed",
                    status = HttpStatusCode.InternalServerError
                )
            }

            /**
             * add member to group
             */
            post(HttpRoutes.Group.ADD_MEMBER) {
                val addMemberRequest = call.receive<AddMemberToGroupRequest>()

                val group = groupColl.addUser(
                    addMemberRequest.groupId,
                    userIds = addMemberRequest.userIds,
                    asAdmin = addMemberRequest.asAdmin
                ) ?: return@post call.respondText(
                    "Unable to add member to group",
                    status = HttpStatusCode.InternalServerError
                )

                call.respond(group)
            }

            /**
             * get all group by user id that the user member of
             */
            get("${HttpRoutes.Group.ALL_FOR_USER}/{id}") {
                val id = call.parameters["id"]
                    ?: return@get call.respondText(
                        "No ID Provided",
                        status = HttpStatusCode.BadRequest
                    )

                val groupList = groupColl.getByUser(id)

                if (groupList.isNotEmpty()) call.respond(groupList)
                else call.respondText(
                    "Does not have any group",
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }
}