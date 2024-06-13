package jp.ac.it_college.std.s23024.book.manager.application.service

import jp.ac.it_college.std.s23024.book.manager.domain.model.Book
import jp.ac.it_college.std.s23024.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s23024.book.manager.domain.exception.BookIdAlreadyInUseException
import jp.ac.it_college.std.s23024.book.manager.domain.exception.BookNotFoundException
import kotlinx.datetime.LocalDate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminBookService(
    private val bookRepository: BookRepository
) {
    @Transactional
    fun register(book: Book) {
        bookRepository.findWithRental(book.id)?.let {
            throw BookIdAlreadyInUseException("既に存在する書籍ID: ${book.id}")
        }
        bookRepository.register(book)
    }
    @Transactional
    fun update(id: Long, title: String?, author: String?, relaseDate: LocalDate?) {
        //存在する書籍IDだったらエラーにする
        bookRepository.findWithRental(id) ?: throw BookNotFoundException("存在しない書籍ID: $id")

        bookRepository.update(id, title, author, relaseDate)
    }

    @Transactional
    fun delete(id: Long) {
        //存在しない書籍IDだったらエラーにする
        bookRepository.findWithRental(id) ?: throw BookNotFoundException("存在しない書籍　$id")

        bookRepository.delete(id)
    }
}