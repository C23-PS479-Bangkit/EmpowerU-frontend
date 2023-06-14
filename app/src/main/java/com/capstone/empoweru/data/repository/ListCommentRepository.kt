package com.capstone.empoweru.data.repository

import com.capstone.empoweru.data.remote.ApiConfig
import com.capstone.empoweru.data.response.CommentList

class ListCommentRepository {
    private val apiService = ApiConfig.apiService

    suspend fun getListOfComments(gmapsId: String): List<CommentList> {
        val response = apiService.getListOfComments(gmapsId)
        if (response.isSuccessful) {
            return response.body()?.result ?: emptyList()
        } else {
            // Handle error case
            throw Exception("Failed to fetch comments")
        }
    }
}

