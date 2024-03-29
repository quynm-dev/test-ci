package com.ktor.skeleton.controller

import com.github.michaelbull.result.mapBoth
import com.ktor.skeleton.config.withRoles
import com.ktor.skeleton.const.Role
import com.ktor.skeleton.data.dto.session.UserSessionDto
import com.ktor.skeleton.data.dto.user.request.AuthenticateUserRequestDto
import com.ktor.skeleton.data.dto.user.request.CreateUserRequestDto
import com.ktor.skeleton.data.dto.user.response.AuthenticateUserResponseDto
import com.ktor.skeleton.service.user.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import com.ktor.skeleton.helper.logger
import com.ktor.skeleton.service.book.BookService
import com.ktor.skeleton.helper.getResultValueOrHandleError
import com.ktor.skeleton.helper.handleError
import com.ktor.skeleton.mapper.toUserSessionDto
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.sessions.*

fun Route.userController() {
    val userService by inject<UserService>()
    val bookService by inject<BookService>()

    route("/users") {
        post("/authenticate") {
            logger.debug { "POST: /users/authenticate" }
            val authenticateUserRequestDto = call.receive<AuthenticateUserRequestDto>()
            val authenticateUserDto = userService.authenticate(authenticateUserRequestDto)
                .getResultValueOrHandleError(this)

            call.sessions.set<UserSessionDto>(authenticateUserDto.user.toUserSessionDto())
            call.respond(HttpStatusCode.OK,
                AuthenticateUserResponseDto(authenticateUserDto.accessToken, authenticateUserDto.refreshToken))
        }

        post("/register") {
            logger.debug { "POST: /register" }
            val createUserRequestDto = call.receive<CreateUserRequestDto>().validate()
            userService.create(createUserRequestDto).mapBoth(
                success = { call.respond(HttpStatusCode.OK, it) },
                failure = { handleError(it) }
            )
        }

        authenticate {
            withRoles(Role.Admin.toString()) {
                get {
                    logger.debug { "GET: /users" }
                    userService.list().mapBoth(
                        success = { call.respond(HttpStatusCode.OK, it) },
                        failure = { handleError(it) }
                    )
                }

                delete("{id?}") {
                    logger.debug { "DELETE: /users/{id?}" }
                }
            }

            get("{id?}") {
                logger.debug { "GET: /users/{id?}" }
            }

            get("/current/books") {
                logger.debug { "GET: {id?}/books" }
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()
                bookService.get(userId).mapBoth(
                    success = { call.respond(HttpStatusCode.OK, it) },
                    failure = { handleBookError(it) }
                )
            }

            put("{id?}") {
                logger.debug { "PUT: /users/{id?}" }
            }
        }
    }
}
