package com.ktor.skeleton.helper

import io.konform.validation.Invalid
import io.konform.validation.Validation
import io.ktor.server.plugins.*

fun <T> Validation<T>.throwOnFailure(value: T): T {
    val result = validate(value)
    if (result is Invalid<T>) {
        throw BadRequestException(result.errors[0].message)
    }

    return value
}