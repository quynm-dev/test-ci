package com.ktor.skeleton.service.user

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.ktor.skeleton.data.model.UserModel
import com.ktor.skeleton.error.ErrorCode
import com.ktor.skeleton.error.user.UserError
import com.ktor.skeleton.mapper.toDto
import com.ktor.skeleton.repository.user.UserRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class UserServiceTest {
    companion object {
        private lateinit var userRepositoryMock: UserRepository
        private lateinit var userService: UserService

        @JvmStatic
        @BeforeAll
        fun setUp() {
            userRepositoryMock = mockk<UserRepository>()
            userService = UserService(userRepositoryMock)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            clearAllMocks()
        }
    }

    @Test
    fun testUserService_list_ReturnExposedSQLException() = runBlocking {
        val error = UserError.DBOperation(ErrorCode.UserError.DBOperationError, "DBOperationError")
        val exposedSQLException = mockk<ExposedSQLException>()

        coEvery { userRepositoryMock.list() } throws exposedSQLException

        val result = userService.list().getError()!!
        Assertions.assertEquals(result.code, error.code)
        Assertions.assertEquals(result.message, error.message)
    }

    @Test
    fun testUserService_list_ReturnException() = runBlocking {
        val error = UserError.InternalServerError(ErrorCode.UserError.InternalServerError, "InternalServerError")
        val exception = mockk<Exception>()

        coEvery { userRepositoryMock.list() } throws exception

        val result = userService.list().getError()!!
        Assertions.assertEquals(result.code, error.code)
        Assertions.assertEquals(result.message, error.message)
    }

    @Test
    fun testUserService_list_ReturnListUserResponseDto() = runBlocking {
        val userModel = UserModel(0, "john_doe", null, "John Doe", "john@gmail.com",
            30, 1)
        val userResponseDto = userModel.toDto()

        coEvery { userRepositoryMock.list() } returns listOf(userModel)

        val result = userService.list().get()!!
        Assertions.assertEquals(result.size, 1)
        Assertions.assertEquals(result[0], userResponseDto)
    }
}