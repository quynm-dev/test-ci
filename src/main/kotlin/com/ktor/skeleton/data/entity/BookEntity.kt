package com.ktor.skeleton.data.entity

import com.ktor.skeleton.data.entity.Users.default
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Books: IntIdTable("books") {
    val title = varchar("title", 255)
    val userId = reference("user", Users)
    val author = varchar("author", 64)
    val isbn = varchar("isbn", 64)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
}

class BookEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<BookEntity>(Books)

    var title by Books.title
    var userId by UserEntity referencedOn Books.userId
    var author by Books.author
    var isbn by Books.isbn
    var createdAt by Books.createdAt
    var updatedAt by Books.updatedAt
}