package com.ktor.skeleton.mapper

import com.ktor.skeleton.const.Role
import com.ktor.skeleton.data.dto.user.request.AuthenticateUserRequestDto
import com.ktor.skeleton.data.dto.user.request.CreateUserRequestDto
import com.ktor.skeleton.data.dto.user.response.UserResponseDto
import com.ktor.skeleton.data.entity.UserEntity
import com.ktor.skeleton.data.model.AuthenticateUserModel
import com.ktor.skeleton.data.model.UserModel
import com.ktor.skeleton.helper.getKeyByValue

fun UserEntity.toModel(): UserModel {
    return UserModel(id = this.id.value, username = this.username, password = this.password, name = this.name, email = this.email,
        age = this.age, role = this.role, createdAt = this.createdAt, updatedAt = this.updatedAt)
}

fun UserModel.toDto(): UserResponseDto {
    return UserResponseDto(username = this.username, name = this.name, email = this.email, age = this.age,
        role = Role::number.getKeyByValue(this.role).toString(), createdAt = this.createdAt)
}

fun CreateUserRequestDto.toModel(): UserModel {
    return UserModel(username = this.username, password = this.password, name = this.name, email = this.email,
        role = this.role, age = this.age)
}

fun AuthenticateUserRequestDto.toModel(): AuthenticateUserModel {
    return AuthenticateUserModel(username = this.username, password = this.password)
}