package jp.ac.it_college.std.s23024.book.manager.application.service.security

import jp.ac.it_college.std.s23024.book.manager.domain.exception.BookNotAvailableException
import jp.ac.it_college.std.s23024.book.manager.domain.exception.RentalStateException
import jp.ac.it_college.std.s23024.book.manager.domain.model.Rental
import jp.ac.it_college.std.s23024.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s23024.book.manager.domain.repository.RentalRepository
import jp.ac.it_college.std.s23024.book.manager.domain.repository.UserRepository
import kotlinx.datetime.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private const val RENTAL_TEAM_DAYS = 14L

@Service
class RentalService(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val rentalRepository: RentalRepository
) {
    @Transactional
    fun startRental(bookId: Long, userId: Long) {
        // ユーザーが存在するか確認
        userRepository.find(userId) ?: RentalStateException("該当するユーザがいません")
        // 本が存在するか確認
        val book = bookRepository.findWithRental(bookId)
            ?: throw RentalStateException("該当する書籍がありません")
        // 貸し出し中チェック
        if (book.isRental) {
            throw BookNotAvailableException("貸し出し中です")
        }

        //  現在日時(Instant型)
        val current = Clock.System.now()
        //  借りた日時(LocalDateTime型)
        val rentalDetetime = current.toLocalDateTime(TimeZone.currentSystemDefault())
        //  返却期限(LocalDateTime型) → current + RENTAL_TERM_DAYS(単位: 日)
        val returnDeadline = current.plus(
            RENTAL_TEAM_DAYS, DateTimeUnit.DAY, TimeZone.currentSystemDefault()
        ).toLocalDateTime(TimeZone.currentSystemDefault())
        val rental = Rental(bookId, userId, rentalDetetime, returnDeadline)

        rentalRepository.startRental(rental)
    }
}