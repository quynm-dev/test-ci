package com.ktor.skeleton.data.dto.error.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val code: Int,
    val message: String
)