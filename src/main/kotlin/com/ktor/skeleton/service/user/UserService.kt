package com.ktor.skeleton.service.user

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.ktor.skeleton.data.dto.user.response.UserResponseDto
import com.ktor.skeleton.error.ErrorCode
import com.ktor.skeleton.error.user.UserError
import com.ktor.skeleton.repository.user.UserRepository
import org.koin.core.annotation.Singleton
import com.ktor.skeleton.helper.logger
import org.jetbrains.exposed.exceptions.ExposedSQLException
import com.github.michaelbull.result.Err
import com.ktor.skeleton.data.dto.user.request.CreateUserRequestDto
import com.ktor.skeleton.mapper.toDto
import com.ktor.skeleton.mapper.toModel

@Singleton
class UserService(private val userRepository: UserRepository): IUserService {
    override suspend fun list(): Result<List<UserResponseDto>, UserError> {
        return try {
            logger.debug { "[UserService:list]" }
            val listUserModels = userRepository.list()

            Ok(listUserModels.map { it.toDto() })
        } catch (err: ExposedSQLException) {
            logger.error { err.message }
            Err(UserError.DBOperation(ErrorCode.UserError.DBOperationError, "ExposedSQLException"))
        } catch (err: Exception) {
            logger.error { err.message }
            Err(UserError.InternalServerError(ErrorCode.UserError.InternalServerError, "InternalServerError"))
        }
    }

    override suspend fun create(createUserRequestDto: CreateUserRequestDto): Result<UserResponseDto, UserError> {
       return try {
           logger.debug { "[UserService:create]" }
           val createUserModel = createUserRequestDto.toModel()
           val userModel = userRepository.create(createUserModel)

           Ok(userModel.toDto())
       } catch (err: ExposedSQLException) {
           logger.error { err.message }
           Err(UserError.DBOperation(ErrorCode.UserError.DBOperationError, "ExposedSQLException"))
       } catch (err: Exception) {
           logger.error { err.message }
           Err(UserError.InternalServerError(ErrorCode.UserError.InternalServerError, "InternalServerError"))
       }
    }

    override suspend fun get(): Result<UserResponseDto, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(): Result<UserResponseDto, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(): Result<Boolean, UserError> {
        TODO("Not yet implemented")
    }
}