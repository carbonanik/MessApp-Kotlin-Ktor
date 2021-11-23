package com.example.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import java.util.*

class JwtConfig(jwtSecret: String) {

    companion object {
        private const val jwtIssuer = "http://0.0.0.0:8080/"
        private const val jwtRealm = "Access to 'hello'"

        private const val CLAIM_PHONE = "phone"
        private const val CLAIM_NAME = "name"
        private const val CLAIM_ID = "id"
    }

    private val jwtAlgorithm = Algorithm.HMAC256(jwtSecret)
    private val jwtVerifier =
        JWT.require(jwtAlgorithm)
            .withIssuer(jwtIssuer)
            .build()

//    val expireTime = Date(System.currentTimeMillis() + 1000L * 60L * 60L * 24L * 30L)
    fun generateToken(user: JwtUser): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM_PHONE, user.phone)
        .withClaim(CLAIM_NAME, user.name)
        .withClaim(CLAIM_ID, user.id)
        .withExpiresAt(Date(System.currentTimeMillis() + 2.628e+9.toLong()))
        .sign(jwtAlgorithm)

    fun configureKtorFeature(
        config: JWTAuthenticationProvider.Configuration
    ) = with(config) {

        verifier(jwtVerifier)
        realm = jwtRealm

        validate {
            val id = it.payload.getClaim(CLAIM_ID).asString()
            val phone = it.payload.getClaim(CLAIM_PHONE).asString()
            val name = it.payload.getClaim(CLAIM_NAME).asString()
            if (!phone.isNullOrEmpty() && !name.isNullOrEmpty()) {
                JwtUser(id, phone, name)
            } else {
                null
            }
        }
    }

    data class JwtUser(val id: String, val phone: String, val name: String) : Principal
}