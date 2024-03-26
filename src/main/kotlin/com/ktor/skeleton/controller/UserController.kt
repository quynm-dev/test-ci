package com.ktor.skeleton.controller

import com.github.michaelbull.result.mapBoth
import com.ktor.skeleton.data.dto.error.response.ErrorResponseDto
import com.ktor.skeleton.data.dto.user.request.CreateUserRequestDto
import com.ktor.skeleton.error.user.UserError
import com.ktor.skeleton.service.user.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import com.ktor.skeleton.helper.logger
import io.ktor.server.request.*
import io.ktor.util.pipeline.*

fun Route.userController() {
    val userService by inject<UserService>()

    route("/users") {
        get {
            logger.debug { "GET: /users" }
            userService.list().mapBoth(
                success = { call.respond(HttpStatusCode.OK, it) },
                failure = { handleUserError(it) }
            )
        }

        post {
            logger.debug { "POST: /users" }
            val createUserRequestDto = call.receive<CreateUserRequestDto>().validate()
            userService.create(createUserRequestDto).mapBoth(
                success = { call.respond(HttpStatusCode.OK, it) },
                failure = { handleUserError(it) }
            )
        }

        get("{id?}") {
            logger.debug { "GET: /users/{id?} " }
        }

        put("{id?}") {
            logger.debug { "PUT: /users/{id?}" }
        }

        delete("{id?}") {
            logger.debug { "DELETE: /users/{id?}" }
        }
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.handleUserError(
    error: UserError
) {
    val statusCode = when (error) {
        is UserError.Unauthorized -> HttpStatusCode.Unauthorized
        is UserError.Forbidden -> HttpStatusCode.Forbidden
        is UserError.NotFound -> HttpStatusCode.NotFound
        is UserError.Conflict -> HttpStatusCode.Conflict
        else -> HttpStatusCode.InternalServerError
    }

    call.respond(statusCode, ErrorResponseDto(error.code.number, error.message))
}