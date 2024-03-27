package com.ktor.skeleton.service.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
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
import com.ktor.skeleton.data.dto.user.request.AuthenticateUserRequestDto
import com.ktor.skeleton.data.dto.user.request.CreateUserRequestDto
import com.ktor.skeleton.data.dto.user.response.AuthenticateUserResponseDto
import com.ktor.skeleton.data.model.UserModel
import com.ktor.skeleton.mapper.toDto
import com.ktor.skeleton.mapper.toModel
import io.ktor.server.plugins.*
import java.util.*

private val JWT_SECRET = System.getenv("JWT_SECRET")
private val JWT_ISSUER = System.getenv("JWT_ISSUER")
private val JWT_AUDIENCE = System.getenv("JWT_AUDIENCE")
private const val ACCESS_TOKEN_EXPIRES_MILLIS = 60 * 60 * 1000
private const val REFRESH_TOKEN_EXPIRES_MILLIS = 24 * 60 * 60 * 1000

@Singleton
class UserService(private val userRepository: UserRepository): IUserService {
    override suspend fun list(): Result<List<UserResponseDto>, UserError> {
        return try {
            logger.debug { "[UserService:list]" }
            val listUserModels = userRepository.list()

            Ok(listUserModels.map { it.toDto() })
        } catch (err: ExposedSQLException) {
            logger.error { err.message }
            Err(UserError.DBOperation(ErrorCode.UserError.DBOperationError, "DBOperationError"))
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
           Err(UserError.DBOperation(ErrorCode.UserError.DBOperationError, "DBOperationError"))
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

    override suspend fun authenticate(authenticateUserRequestDto: AuthenticateUserRequestDto):
            Result<AuthenticateUserResponseDto, UserError> {
        return try {
            logger.debug { "[UserService:authenticate]" }
            val authenticateUserModel = authenticateUserRequestDto.toModel()
            val userModel = userRepository.authenticate(authenticateUserModel)

            val (accessToken, refreshToken) = buildAuthenticateCredentials(userModel)
            Ok(AuthenticateUserResponseDto(accessToken, refreshToken))
        } catch (err: IllegalArgumentException) {
            logger.error { err.message }
            Err(UserError.Unauthorized(ErrorCode.UserError.Unauthorized, "Unauthorized"))
        } catch (err: NotFoundException) {
            logger.error { err.message }
            Err(UserError.NotFound(ErrorCode.UserError.NotFound, "NotFound"))
        } catch (err: ExposedSQLException) {
            logger.error { err.message }
            Err(UserError.DBOperation(ErrorCode.UserError.DBOperationError, "DBOperationError"))
        } catch (err: Exception) {
            logger.error { err.message }
            Err(UserError.InternalServerError(ErrorCode.UserError.InternalServerError, "InternalServerError"))
        }
    }

    private fun buildAuthenticateCredentials(userModel: UserModel): Pair<String, String> {
        val accessToken = JWT.create()
            .withAudience(JWT_AUDIENCE)
            .withIssuer(JWT_ISSUER)
            .withClaim("userId", userModel.id)
            .withClaim("username", userModel.username)
            .withClaim("name", userModel.name)
            .withClaim("email", userModel.email)
            .withClaim("age", userModel.age)
            .withExpiresAt(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES_MILLIS))
            .sign(Algorithm.HMAC256(JWT_SECRET))
        val refreshToken = JWT.create()
            .withAudience(JWT_AUDIENCE)
            .withIssuer(JWT_ISSUER)
            .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRES_MILLIS))
            .sign(Algorithm.HMAC256(JWT_SECRET))

        return Pair(accessToken, refreshToken)
    }
}