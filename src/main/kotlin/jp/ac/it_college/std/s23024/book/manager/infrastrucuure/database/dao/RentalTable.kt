package jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.dao

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object RentalTable : LongIdTable("rental") {
    val book = reference("book_id", BookTable)
    val user = reference("user_id", UserTable)
    val rentalDateTime = datetime("rental_datetime")
    val returnDeadline = datetime("return_deadline")
}