package jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.dao

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object BookTable : LongIdTable("book") {
    val title = varchar("title", 128)
    val author = varchar("author", 32)
    val releaseData = date("release_date")
}