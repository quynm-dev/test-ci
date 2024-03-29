package com.ktor.skeleton.service.redis

import io.ktor.server.plugins.*
import io.ktor.server.sessions.*
import redis.clients.jedis.JedisPool

private const val KEY_PREFIX = "ss:"
private const val EXPIRES_DURATION: Long = 30 * 24 * 60 * 60

class RedisSessionStorage(private val jedisPool: JedisPool): SessionStorage {
    override suspend fun invalidate(id: String) {
        val key = getSessionKey(id)
        jedisPool.resource.use { jedis -> jedis.del(key) }
    }

    override suspend fun read(id: String): String {
        val key = getSessionKey(id)
        jedisPool.resource.use { jedis ->
            jedis[key]?.let { return it } ?: throw NotFoundException("Session id is not found")
        }
    }

    override suspend fun write(id: String, value: String) {
        val key = getSessionKey(id)
        jedisPool.resource.use { jedis ->
            jedis.set(key, value)
            jedis.expire(key, EXPIRES_DURATION)
        }
    }

    private fun getSessionKey(id: String): String {
        if (id.isEmpty()) { throw IllegalArgumentException("Session id is empty") }

        return KEY_PREFIX + id
    }
}