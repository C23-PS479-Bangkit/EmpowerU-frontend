package com.capstone.empoweru.data.request

data class CommentRequest(
    val GMapsID: String,
    val userID: String,
    val starRating: Int,
    val comment: String
)
