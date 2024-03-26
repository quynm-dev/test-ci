package com.ktor.skeleton.error

enum class ErrorCode() {;
    enum class UserError(val code: Int) {
        Unauthorized(100),
        Forbidden(101),
        NotFound(102),
        Conflict(103),
        DBOperationError(104),
        InternalServerError(105)
    }
}