package jp.ac.it_college.std.s23024.book.manager.domain.model

data class BookWithRental(
    val book: Book,
    val rental: Rental?
) {
    val isRental: Boolean
        get() = rental != null
}
