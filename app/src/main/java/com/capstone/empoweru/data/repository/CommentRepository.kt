package com.capstone.empoweru.data.repository

import com.capstone.empoweru.data.remote.ApiService
import com.capstone.empoweru.data.request.CommentRequest
import com.capstone.empoweru.data.response.CommentResponse

class CommentRepository(private val apiService: ApiService) {
    suspend fun createComment(request: CommentRequest): CommentResponse {
        return apiService.createComment(request)
    }
}