package com.ktor.skeleton.data.model

import com.ktor.skeleton.helper.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

data class UserModel(
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val age: Int?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime
)