package com.ktor.skeleton.data.dto.session

import io.ktor.server.auth.*

data class UserSessionDto(
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val age: Int?,
    val role: String
): Principal