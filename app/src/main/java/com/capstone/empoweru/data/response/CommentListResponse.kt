package com.capstone.empoweru.data.response

data class CommentListResponse(
    val result: List<CommentList>
)

data class CommentList(
    val username: String,
    val starRating: Double,
    val comment: String,
    val urlPhoto: String
)

