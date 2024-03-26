package com.ktor.skeleton.data.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Users: IntIdTable("users") {
    val username = varchar("username", 64).uniqueIndex()
    val password = varchar("password", 64)
    val name = varchar("name", 64)
    val email = varchar("email", 64)
    val age = integer("age").nullable()
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
}

class UserEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<UserEntity>(Users)

    var username by Users.username
    var password by Users.password
    var name by Users.name
    var email by Users.email
    var age by Users.age
    var createdAt by Users.createdAt
    var updatedAt by Users.updatedAt
}