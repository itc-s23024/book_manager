package jp.ac.it_college.std.s23024.book.manager.presentation.form

import kotlinx.serialization.Serializable

@Serializable
data class RentalStartRequest(
    val bookId: Long
)
