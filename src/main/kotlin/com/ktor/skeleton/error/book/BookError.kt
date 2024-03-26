package com.ktor.skeleton.error.book

import com.ktor.skeleton.error.ErrorCode

sealed class BookError(val code: ErrorCode.BookError, val message: String) {
    class Unauthorized(code: ErrorCode.BookError, message: String): BookError(code, message)
    class Forbidden(code: ErrorCode.BookError, message: String): BookError(code, message)
    class NotFound(code: ErrorCode.BookError, message: String): BookError(code, message)
    class Conflict(code: ErrorCode.BookError, message: String): BookError(code, message)
    class DBOperation(code: ErrorCode.BookError, message: String): BookError(code, message)
    class InternalServerError(code: ErrorCode.BookError, message: String): BookError(code, message)
}
