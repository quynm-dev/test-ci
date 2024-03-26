package com.ktor.skeleton.error

enum class ErrorCode() {;
    enum class UserError(val number: Int) {
        Unauthorized(100),
        Forbidden(101),
        NotFound(102),
        Conflict(103),
        BadRequest(104),
        DBOperationError(105),
        InternalServerError(106)
    }

    enum class BookError(val number: Int) {
        Unauthorized(100),
        Forbidden(101),
        NotFound(102),
        Conflict(103),
        BadRequest(104),
        DBOperationError(105),
        InternalServerError(106)
    }
}