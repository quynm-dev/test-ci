package com.ktor.skeleton.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ktor.skeleton.data.dto.error.response.ErrorResponseDto
import com.ktor.skeleton.error.ErrorCode
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

private val JWT_SECRET = System.getenv("JWT_SECRET")
private val JWT_ISSUER = System.getenv("JWT_ISSUER")
private val JWT_AUDIENCE = System.getenv("JWT_AUDIENCE")
private val JWT_REALM = System.getenv("JWT_REALM")

fun Application.configureAuthentication() {
    install(Authentication) {
        jwt {
            realm = JWT_REALM
            verifier(JWT
                .require(Algorithm.HMAC256(JWT_SECRET))
                .withAudience(JWT_AUDIENCE)
                .withIssuer(JWT_ISSUER)
                .build()
            )
            validate { credential -> JWTPrincipal(credential.payload) }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized,
                    ErrorResponseDto(ErrorCode.UserError.Unauthorized.number, "Unauthorized")) }
        }
    }
}