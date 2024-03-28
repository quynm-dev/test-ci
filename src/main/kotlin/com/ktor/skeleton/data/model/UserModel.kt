package com.ktor.skeleton.data.model

import java.time.LocalDateTime

data class UserModel(
    val id: Int = 0,
    val username: String,
    val password: String?,
    val name: String,
    val email: String,
    val age: Int?,
    val role: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

data class AuthenticateUserModel(
    val username: String,
    val password: String
)