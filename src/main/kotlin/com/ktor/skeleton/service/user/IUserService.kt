package com.ktor.skeleton.service.user

import com.github.michaelbull.result.Result
import com.ktor.skeleton.data.dto.user.request.CreateUserRequestDto
import com.ktor.skeleton.data.dto.user.response.UserResponseDto
import com.ktor.skeleton.error.user.UserError

interface IUserService {
    suspend fun list(): Result<List<UserResponseDto>, UserError>
    suspend fun create(createUserRequestDto: CreateUserRequestDto): Result<UserResponseDto, UserError>
    suspend fun get(): Result<UserResponseDto, UserError>
    suspend fun update(): Result<UserResponseDto, UserError>
    suspend fun delete(): Result<Boolean, UserError>
}