package com.example.entity

import com.example.authentication.JwtUser
import io.ktor.http.cio.websocket.*

data class Connection(val session: DefaultWebSocketSession, val user: JwtUser)