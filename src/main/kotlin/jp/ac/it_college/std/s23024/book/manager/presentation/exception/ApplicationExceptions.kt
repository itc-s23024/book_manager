package jp.ac.it_college.std.s23024.book.manager.presentation.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class BookNotFoundException(override val message: String) : Exception()
@ResponseStatus(HttpStatus.CONFLICT)
class BookIdAlreadyInUseException(override val message: String) : Exception(message)