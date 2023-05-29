package com.capstone.empoweru.data.dummy

import com.capstone.empoweru.R

data class Comment(
    val username: String,
    val profileImage: Int,
    val text: String,
    val image: Int? = null,
    val rating: Float
)

fun generateDummyComments(): List<Comment> {
    return listOf(
        Comment(
            username = "User1",
            profileImage = R.drawable.profile_dummy,
            text = "This place is amazing!",
            rating = 4.5f
        ),
        Comment(
            username = "User2",
            profileImage = R.drawable.profile_dummy,
            text = "Great service and delicious food!",
            rating = 5.0f
        ),
        Comment(
            username = "User3",
            profileImage = R.drawable.profile_dummy,
            image = R.drawable.dummy_umkm,
            text = "Here's a photo from my visit.",
            rating = 3.5f
        ),
        Comment(
            username = "User4",
            profileImage = R.drawable.profile_dummy,
            image = R.drawable.dummy_umkm,
            text = "Here's a photo from my visit.",
            rating = 4.5f
        ),
        Comment(
            username = "User5",
            profileImage = R.drawable.profile_dummy,
            text = "I love it.",
            rating = 4.0f
        ),
    )
}
