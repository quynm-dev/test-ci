package com.ktor.skeleton.data.model

import java.time.LocalDateTime

data class BookModel(
    val id: Int,
    val title: String,
    val user: UserModel,
    val author: String,
    val isbn: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)