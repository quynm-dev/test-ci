package com.ktor.skeleton.helper

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.ktor.skeleton.data.dto.error.response.ErrorResponseDto
import com.ktor.skeleton.error.user.UserError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

suspend fun <V, E> Result<V, E>.getResultValueOrHandleError(context: PipelineContext<Unit, ApplicationCall>): V {
    val value = this.get()
    if (value == null) {
        val error = this.getError()!!
        logger.error { error.toString() }
        context.handleError(error)
    }

    return value!!
}

suspend fun PipelineContext<Unit, ApplicationCall>.handleError(
    error: Any
) {
    if (error is UserError) {
        val statusCode = when (error) {
            is UserError.Unauthorized -> HttpStatusCode.Unauthorized
            is UserError.Forbidden -> HttpStatusCode.Forbidden
            is UserError.NotFound -> HttpStatusCode.NotFound
            is UserError.Conflict -> HttpStatusCode.Conflict
            else -> HttpStatusCode.InternalServerError
        }

        call.respond(statusCode, ErrorResponseDto(error.code.number, error.message))
    }
}