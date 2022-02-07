package com.example.routing

sealed class HttpRoutes(val route: String) {

    object Auth : HttpRoutes("/auth") {
        const val LOGIN = "/login"
        const val SIGNUP = "/signup"
        const val REFRESH = "/refresh"
    }

    object User : HttpRoutes("/user") {
        const val ME = "/me"
        const val GET_ALL = "/get-all"
        const val GET_BY_NAME = "/get-by-name"
        const val GET_BY_ID = "/get-by-id"
        const val GET_BY_PHONE = "/get-by-phone"
        const val DELETE = "/delete"
    }

    object Group : HttpRoutes("/group") {
        const val CREATE = "/create"
        const val GET_BY_ID = "/get-by-id"
        const val ADD_MEMBER = "/add-member"
        const val REMOVE_MEMBER = "/remove-member"
        const val ALL_FOR_USER = "/all-for-user"
    }

    object Post : HttpRoutes("/post") {
        const val CREATE = "/create"
        const val GET_BY_ID = "/get-by-id"
        const val GET_OF_USER = "/get-of-user"
        const val GET_ALL_PAGED = "/get-all"
        const val GET_ALL_UNTIL = "/get-all-until"
        const val GET_ALL_BETWEEN = "/get-all-between"
        const val DELETE = "/delete"
    }

    object Message : HttpRoutes("/message") {
        const val GET_ALL_BY_RECEIVER = "/get-all-by-receiver"
        const val UNREAD_FOR_USER = "/unread-for-user"
    }

    object Socket : HttpRoutes("/socket") {
        const val CHAT = "/chat"
        const val USERS_ONLINE = "/check-users-online"
    }

    object File : HttpRoutes("/file")
}
