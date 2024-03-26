package com.ktor.skeleton.repository.user

import com.ktor.skeleton.data.entity.UserEntity
import com.ktor.skeleton.data.entity.Users
import com.ktor.skeleton.data.model.UserModel
import com.ktor.skeleton.helper.logger
import com.ktor.skeleton.helper.wrapperTransaction
import com.ktor.skeleton.mapper.toModel
import com.toxicbakery.bcrypt.Bcrypt
import org.jetbrains.exposed.sql.selectAll
import org.koin.core.annotation.Singleton

private val PASSWORD_SALT = System.getenv("PASSWORD_SALT")

@Singleton
class UserRepository: IUserRepository {
    override suspend fun list(): List<UserModel> {
        return wrapperTransaction {
            logger.debug { "[UserRepository:list]" }

            Users.selectAll().map { UserEntity.wrapRow(it).toModel() }
        }
    }

    override suspend fun create(userModel: UserModel): UserModel {
        return wrapperTransaction {
            logger.debug { "[UserRepository:create]" }
            val userEntity =  UserEntity.new {
                username = userModel.username
                password = Bcrypt.hash(userModel.password.toString() + PASSWORD_SALT, 10).toString()
                name = userModel.name
                email = userModel.email
                age = userModel.age
            }

            return@wrapperTransaction userEntity.toModel()
        }
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