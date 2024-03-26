package com.ktor.skeleton.service.book

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.ktor.skeleton.data.dto.book.response.BookResponseDto
import com.ktor.skeleton.error.ErrorCode
import com.ktor.skeleton.error.book.BookError
import com.ktor.skeleton.helper.logger
import com.ktor.skeleton.mapper.toDto
import com.ktor.skeleton.repository.book.BookRepository
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.koin.core.annotation.Singleton

@Singleton
class BookService(private val bookRepository: BookRepository): IBookService {
    override suspend fun list(): Result<List<BookResponseDto>, BookError> {
        return try {
            logger.debug { "[BookService:list]" }
            val listBookModels = bookRepository.list()

            Ok(listBookModels.map { it.toDto() })
        } catch (err: ExposedSQLException) {
            logger.error { err.message }
            Err(BookError.DBOperation(ErrorCode.BookError.DBOperationError, "ExposedSQLException"))
        } catch (err: Exception) {
            logger.error { err.message }
            Err(BookError.InternalServerError(ErrorCode.BookError.InternalServerError, "InternalServerError"))
        }
    }

    override suspend fun create(): Result<BookResponseDto, BookError> {
        TODO("Not yet implemented")
    }

    override suspend fun get(): Result<BookResponseDto, BookError> {
        TODO("Not yet implemented")
    }

    override suspend fun get(userId: Int): Result<List<BookResponseDto>, BookError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(): Result<BookResponseDto, BookError> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(): Result<Boolean, BookError> {
        TODO("Not yet implemented")
    }
}