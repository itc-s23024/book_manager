package jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.dao

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object VBookWithRental : LongIdTable("v_book_with_rental") {
    val title = varchar("title", 128)
    val author = varchar("author", 32)
    val releaseDate = date("release_date")
    val useid = long("user_id").nullable()
    val rentalDateTime = datetime("rental_datetime").nullable()
    val returnDeadline = datetime("return_deadline").nullable()
}