package com.capstone.empoweru.data.dummy

data class Comment(
    val username: String,
    val starRating: Double,
    val comment: String
)

fun generateDummyComments(): List<Comment> {
    return listOf(
        Comment(
            username = "User1",
            starRating = 4.5,
            comment = "Makananya sangat enak!"
        ),
        Comment(
            username = "User2",
            starRating = 4.5,
            comment = "Makananya sangat enak!"
        ),
        Comment(
            username = "User3",
            starRating = 4.5,
            comment = "Makananya sangat enak!"
        ),
        Comment(
            username = "User4",
            starRating = 4.5,
            comment = "Makananya sangat enak!"
        ),
        Comment(
            username = "User5",
            starRating = 4.5,
            comment = "Makananya sangat enak!"
        ),
    )
}
