package com.ktor.skeleton.data.dto.user.response

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import com.ktor.skeleton.helper.LocalDateTimeSerializer

@Serializable
data class UserResponseDto(
    val username: String,
    val name: String,
    val email: String,
    val age: Int?,
    val role: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime
)

@Serializable
data class AuthenticateUserResponseDto(
    val accessToken: String,
    val refreshToken: String
)