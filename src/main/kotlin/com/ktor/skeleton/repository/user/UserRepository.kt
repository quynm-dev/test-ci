package com.ktor.skeleton.repository.user

import com.ktor.skeleton.data.entity.UserEntity
import com.ktor.skeleton.data.entity.Users
import com.ktor.skeleton.data.model.UserModel
import com.ktor.skeleton.helper.logger
import com.ktor.skeleton.helper.wrapperTransaction
import com.ktor.skeleton.mapper.toModel
import org.jetbrains.exposed.sql.selectAll
import org.koin.core.annotation.Singleton

@Singleton
class UserRepository: IUserRepository {
    override suspend fun list(): List<UserModel> {
        return wrapperTransaction {
            logger.debug { "[UserRepository:list]" }

            Users.selectAll().map { UserEntity.wrapRow(it).toModel() }
        }
    }

    override suspend fun create(): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun get(): UserModel? {
        TODO("Not yet implemented")
    }

    override suspend fun update(): UserModel? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(): Boolean {
        TODO("Not yet implemented")
    }
}