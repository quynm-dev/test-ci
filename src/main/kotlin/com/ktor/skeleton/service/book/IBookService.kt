package com.ktor.skeleton.service.book

import com.github.michaelbull.result.Result
import com.ktor.skeleton.data.dto.book.response.BookResponseDto
import com.ktor.skeleton.error.book.BookError

interface IBookService {
    suspend fun list(): Result<List<BookResponseDto>, BookError>
    suspend fun create(): Result<BookResponseDto, BookError>
    suspend fun get(): Result<BookResponseDto, BookError>
    suspend fun get(userId: Int): Result<List<BookResponseDto>, BookError>
    suspend fun update(): Result<BookResponseDto, BookError>
    suspend fun delete(): Result<Boolean, BookError>
}