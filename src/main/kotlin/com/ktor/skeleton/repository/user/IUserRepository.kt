package com.ktor.skeleton.repository.user

import com.ktor.skeleton.data.model.UserModel

interface IUserRepository {
    suspend fun list(): List<UserModel>
    suspend fun create(userModel: UserModel): UserModel
    suspend fun get(): UserModel?
    suspend fun update(): UserModel?
    suspend fun delete(): Boolean
}