package com.example.routing

sealed class HttpRoutes(val route: String) {

    object Auth: HttpRoutes("/auth") {
        const val LOGIN = "/login"
        const val SIGNUP = "/signup"
        const val REFRESH = "/refresh"
    }

    object User: HttpRoutes("/user") {
        const val ME = "/me"
        const val GET_ALL = "/get-all"
        const val GET_BY_NAME = "/get-by-name"
        const val GET_BY_ID = "/get-by-id"
        const val GET_BY_PHONE = "/get-by-phone"
        const val DELETE_BY_ID = "/delete-by-id"
    }

    object Group: HttpRoutes("/group") {
        const val CREATE = "/create"
        const val ADD_MEMBER = "/add-member"
        const val ALL_FOR_USER = "/all-for-user"
    }
    object Post: HttpRoutes("/post"){
        const val CREATE = "/create"
        const val GET_BY_ID = "/get-by-id"
        const val GET_OF_USER = "/get-of-user"
        const val GET_ALL_UNTIL = "/get-all-until"
        const val GET_ALL_BETWEEN = "/get-all-between"
    }
    object File: HttpRoutes("/file")
}
