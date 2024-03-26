package com.ktor.skeleton.data.dto.book.response

import com.ktor.skeleton.data.dto.user.response.UserResponseDto
import com.ktor.skeleton.helper.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class BookResponseDto(
    val title: String,
    val user: UserResponseDto,
    val author: String,
    val isbn: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime
)