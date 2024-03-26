package com.ktor.skeleton.mapper

import com.ktor.skeleton.data.dto.book.response.BookResponseDto
import com.ktor.skeleton.data.entity.BookEntity
import com.ktor.skeleton.data.model.BookModel

fun BookEntity.toModel(): BookModel {
   return BookModel(id = this.id.value, title = this.title, user = this.userId.toModel(), author = this.author,
       isbn = this.isbn, createdAt = this.createdAt, updatedAt = this.updatedAt)
}

fun BookModel.toDto(): BookResponseDto {
    return BookResponseDto(title = this.title, user = this.user.toDto(), author = this.author, isbn = this.isbn,
        createdAt = this.createdAt, updatedAt = this.updatedAt)
}