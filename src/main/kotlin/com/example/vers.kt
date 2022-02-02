package com.example

const val local = "local"
const val remote = "remote"

/**
 *  select local to run on local machine
 *  select remote before deploy remote server
 */
const val server = remote

val port by lazy {
    if (server == remote) System.getenv("PORT").toInt()
    else 8080
}

const val authenticationConfig = "auth-jwt"