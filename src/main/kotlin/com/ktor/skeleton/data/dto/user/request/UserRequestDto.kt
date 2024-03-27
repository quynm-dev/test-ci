package com.ktor.skeleton.data.dto.user.request

import com.ktor.skeleton.helper.throwOnFailure
import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequestDto(
    val username: String,
    val password: String,
    val name: String,
    val email: String,
    val age: Int?,
) {
    fun validate() = Validation {
        CreateUserRequestDto::username required {}
        CreateUserRequestDto::password required {
            minLength(8) hint "Password must have minimum 8 characters"
        }
        CreateUserRequestDto::email required {
            pattern(".+@.+\\..+") hint "Email is invalid"
        }
    }.throwOnFailure(this)
}

@Serializable
data class AuthenticateUserRequestDto(
    val username: String,
    val password: String
)