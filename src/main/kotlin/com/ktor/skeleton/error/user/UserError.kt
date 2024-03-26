package com.ktor.skeleton.error.user

import com.ktor.skeleton.error.ErrorCode

sealed class UserError(val code: ErrorCode.UserError, val message: String) {
    class Unauthorized(code: ErrorCode.UserError, message: String): UserError(code, message)
    class Forbidden(code: ErrorCode.UserError, message: String): UserError(code, message)
    class NotFound(code: ErrorCode.UserError, message: String): UserError(code, message)
    class Conflict(code: ErrorCode.UserError, message: String): UserError(code, message)
    class DBOperation(code: ErrorCode.UserError, message: String): UserError(code, message)
    class InternalServerError(code: ErrorCode.UserError, message: String): UserError(code, message)
}
