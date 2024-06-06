package jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.dao

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object RentalTable : LongIdTable("rental") {
    val bookId = reference("book", BookTable)
    val userId = reference("user", UserTable)
    val rentalDateTime = datetime("rental_datetime")
    val returnDeadline = datetime("return_deadline")
}