package jp.ac.it_college.std.s23024.book.manager.presentation.controller

import jp.ac.it_college.std.s23024.book.manager.application.service.BookService
import jp.ac.it_college.std.s23024.book.manager.presentation.form.BookInfo
import jp.ac.it_college.std.s23024.book.manager.presentation.form.GetBookDetailResponse
import jp.ac.it_college.std.s23024.book.manager.presentation.form.GetBookListResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("book")
class BookController (
    private val bookService: BookService
) {
    @GetMapping("/list")
    fun getList(): GetBookListResponse {
        val bookList = bookService.getList().map(::BookInfo)
        return GetBookListResponse(bookList)
    }
    //追記
    @GetMapping("/detail/{book_id}")
    fun getDetail(@PathVariable("book_id") bookId: Long) : GetBookDetailResponse {
        val book = bookService.getDetail(bookId)
        return GetBookDetailResponse(book)
    }
}