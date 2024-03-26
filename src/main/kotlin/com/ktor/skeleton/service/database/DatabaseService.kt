package com.ktor.skeleton.service.database

import com.ktor.skeleton.data.entity.Books
import com.ktor.skeleton.data.entity.Users
import io.ktor.server.application.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

class DatabaseConfig(
    val dbName: String? = System.getenv("MYSQL_DATABASE"),
    val dbUsername: String? = System.getenv("MYSQL_USER"),
    val dbPassword: String? = System.getenv("MYSQL_PASSWORD"),
    val dbUrl: String? = System.getenv("MYSQL_URL"),
    val dbDriver: String? = System.getenv("MYSQL_DRIVER")
)

class DatabaseService(private val config: DatabaseConfig) {
    fun initConnection(): HikariDataSource {
        val hikariConfig = initHikariConfig()
        val dataSource = HikariDataSource(hikariConfig)
        val database = Database.connect(dataSource)
        LoggerFactory.getLogger(Application::class.simpleName).info("Initialized Database")

        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Users, Books)
        }

        return dataSource
    }

    private fun initHikariConfig(): HikariConfig {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = "${config.dbUrl}/${config.dbName}"
        hikariConfig.username = config.dbUsername
        hikariConfig.password = config.dbPassword
        hikariConfig.driverClassName = config.dbDriver
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true")
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        hikariConfig.isAutoCommit = true

        return hikariConfig
    }
}