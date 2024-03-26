package com.ktor.skeleton.repository.book

import com.ktor.skeleton.data.model.BookModel

interface IBookRepository {
    suspend fun list(): List<BookModel>
    suspend fun create(): Int?
    suspend fun get(): BookModel?
    suspend fun get(userId: Int): List<BookModel>
    suspend fun update(): BookModel?
    suspend fun delete(): Boolean
}