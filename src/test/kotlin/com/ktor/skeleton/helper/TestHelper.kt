package com.ktor.skeleton.helper

import com.ktor.skeleton.service.database.DatabaseConfig
import com.ktor.skeleton.service.database.DatabaseService
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

private const val H2_SQL_DATA_URL = "src/test/resources/mock/data.sql"
private const val H2_DATABASE_DRIVER = "org.h2.Driver"
private const val H2_DATABASE_JDBC_URL = "jdbc:h2:file:./build/db"
private const val H2_DATABASE_DIRECTORY_URL = "build/db"

fun initH2DatabaseConnection() {
    val h2DatabaseConfig = DatabaseConfig(dbUrl = H2_DATABASE_JDBC_URL, dbDriver = H2_DATABASE_DRIVER)
    DatabaseService(h2DatabaseConfig).initConnection()

    transaction {
        val sqlDataFile = File(H2_SQL_DATA_URL)
        if (sqlDataFile.exists()) {
            exec(sqlDataFile.readText())
        }
    }
}

fun clearH2DatabaseFiles() {
    val dbDirectory = File(H2_DATABASE_DIRECTORY_URL)
    if (dbDirectory.exists()) {
        dbDirectory.listFiles()?.forEach { it.delete() }
    }
}