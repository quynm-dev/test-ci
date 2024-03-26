package com.ktor.skeleton.data.model

import java.time.LocalDateTime

data class UserModel(
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val age: Int?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)