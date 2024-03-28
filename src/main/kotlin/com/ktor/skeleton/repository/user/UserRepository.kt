package com.ktor.skeleton.repository.user

import com.ktor.skeleton.data.entity.UserEntity
import com.ktor.skeleton.data.entity.Users
import com.ktor.skeleton.data.model.AuthenticateUserModel
import com.ktor.skeleton.data.model.UserModel
import com.ktor.skeleton.helper.logger
import com.ktor.skeleton.helper.wrapperTransaction
import com.ktor.skeleton.mapper.toModel
import com.toxicbakery.bcrypt.Bcrypt
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.select
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
                password = Bcrypt.hash(userModel.password.toString() + PASSWORD_SALT, 10)
                    .decodeToString()
                name = userModel.name
                email = userModel.email
                age = userModel.age
                role = userModel.role
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

    override suspend fun authenticate(authenticateUserModel: AuthenticateUserModel): UserModel {
        return wrapperTransaction {
            logger.debug { "[UserRepository:authenticate]" }
            val userEntity = Users.select { Users.username eq authenticateUserModel.username }.singleOrNull()
                ?.let { UserEntity.wrapRow(it) }
            if (userEntity == null) {
                throw NotFoundException("User is not found")
            }

            if (!Bcrypt.verify(authenticateUserModel.password + PASSWORD_SALT,
                    userEntity.password.encodeToByteArray())) {
                throw IllegalArgumentException()
            }

            userEntity.toModel()
        }
    }
}