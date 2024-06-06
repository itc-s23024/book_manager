package jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RentalEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<RentalEntity>(RentalTable)

    var book by BookEntity referencedOn RentalTable.book
    var user by UserEntity referencedOn RentalTable.user
    var rentalDateTime by RentalTable.rentalDateTime
    var returnDeadline by RentalTable.returnDeadline
}