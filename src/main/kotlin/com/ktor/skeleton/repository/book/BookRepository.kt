package com.ktor.skeleton.repository.book

import com.ktor.skeleton.data.entity.BookEntity
import com.ktor.skeleton.data.entity.Books
import com.ktor.skeleton.data.model.BookModel
import com.ktor.skeleton.helper.logger
import com.ktor.skeleton.helper.wrapperTransaction
import com.ktor.skeleton.mapper.toModel
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.koin.core.annotation.Singleton

@Singleton
class BookRepository: IBookRepository {
    override suspend fun list(): List<BookModel> {
        return wrapperTransaction {
            logger.debug { "[BookRepository:list]" }

            Books.selectAll().map {
                BookEntity.wrapRow(it).load(BookEntity::userId).toModel()
            }
        }
    }

    override suspend fun create(): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun get(): BookModel? {
        TODO("Not yet implemented")
    }

    override suspend fun get(userId: Int): List<BookModel> {
        return wrapperTransaction {
            logger.debug { "[BookRepository:get]" }

            Books.select { Books.userId eq userId  }
                .orderBy(Books.title, SortOrder.ASC)
                .map { BookEntity.wrapRow(it).load(BookEntity::userId).toModel() }
        }
    }

    override suspend fun update(): BookModel? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(): Boolean {
        TODO("Not yet implemented")
    }
}