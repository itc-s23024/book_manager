package jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BookEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<BookEntity>(BookTable)

    var title by BookTable.title
    var author by BookTable.author
    var releaseDate by BookTable.releaseData

    override fun toString() = """
        BookEntity(id=$id, title=$title, author=$author, releaseDate=$releaseDate)
    """.trimIndent()
}