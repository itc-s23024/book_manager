package jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.repository

import jp.ac.it_college.std.s23024.book.manager.domain.model.Rental
import jp.ac.it_college.std.s23024.book.manager.domain.repository.RentalRepository
import jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.dao.BookEntity
import jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.dao.RentalEntity
import jp.ac.it_college.std.s23024.book.manager.infrastrucuure.database.dao.UserEntity
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class RenatlRepositoryImpl : RentalRepository {
    override fun startRental(rental: Rental) {
        transaction {
            val bookEntity = BookEntity.findById(rental.bookId)
                ?: throw IllegalStateException("書籍ID ${rental.bookId}が見つかりません")
            val userEntity = UserEntity.findById(rental.userId)
                ?: throw IllegalStateException("ユーザーID: ${rental.userId}が見つかりません")

            RentalEntity.new {
                book = bookEntity
                user = userEntity
                rentalDateTime = rental.rentalDateTime
                returnDeadline = rental.returnDeadline
            }
        }
    }
}