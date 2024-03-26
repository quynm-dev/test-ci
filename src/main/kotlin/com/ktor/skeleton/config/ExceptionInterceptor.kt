package com.ktor.skeleton.config

import com.ktor.skeleton.data.dto.error.response.ErrorResponseDto
import com.ktor.skeleton.error.ErrorCode
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureExceptionInterceptor() {
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest,
                ErrorResponseDto(ErrorCode.UserError.BadRequest.number, cause.localizedMessage))
        }
    }
}