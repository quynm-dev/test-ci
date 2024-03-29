package com.ktor.skeleton.config

import com.ktor.skeleton.data.dto.session.UserSessionDto
import com.ktor.skeleton.service.redis.RedisSessionStorage
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import redis.clients.jedis.JedisPool

private val REDIS_HOST = System.getenv("REDIS_HOST")
private val REDIS_PORT = System.getenv("REDIS_PORT")

fun Application.configureSecurity() {
    install(Sessions) {
        val secretSignKey = hex("018d440b22047058ba2cd737d02ae8ef")
        val redisPool = JedisPool(REDIS_HOST, REDIS_PORT.toInt())

        header<UserSessionDto>("Session-Id", RedisSessionStorage(redisPool)) {
            transform(SessionTransportTransformerMessageAuthentication(secretSignKey))
        }
    }
}