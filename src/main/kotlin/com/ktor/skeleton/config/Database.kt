package com.ktor.skeleton.config

import com.ktor.skeleton.service.DatabaseConfig
import com.ktor.skeleton.service.DatabaseService
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*

fun Application.configureDatabase(): HikariDataSource {
    return DatabaseService(DatabaseConfig()).initConnection()
}