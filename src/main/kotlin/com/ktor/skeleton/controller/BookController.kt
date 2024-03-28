package com.ktor.skeleton.controller

import com.github.michaelbull.result.mapBoth
import com.ktor.skeleton.data.dto.error.response.ErrorResponseDto
import com.ktor.skeleton.error.book.BookError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import com.ktor.skeleton.helper.logger
import com.ktor.skeleton.service.book.BookService
import io.ktor.server.auth.*
import io.ktor.util.pipeline.*

fun Route.bookController() {
    val bookService by inject<BookService>()

    authenticate {
        route("/books") {
            get {
                logger.debug { "GET: /books" }
                bookService.list().mapBoth(
                    success = { call.respond(HttpStatusCode.OK, it) },
                    failure = { handleBookError(it) }
                )
            }
        }
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.handleBookError(
    error: BookError
) {
    val statusCode = when (error) {
        is BookError.Unauthorized -> HttpStatusCode.Unauthorized
        is BookError.Forbidden -> HttpStatusCode.Forbidden
        is BookError.NotFound -> HttpStatusCode.NotFound
        is BookError.Conflict -> HttpStatusCode.Conflict
        else -> HttpStatusCode.InternalServerError
    }

    call.respond(statusCode, ErrorResponseDto(error.code.number, error.message))
}