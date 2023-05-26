package com.capstone.empoweru.data.repository

import com.capstone.empoweru.data.remote.ApiConfig
import com.capstone.empoweru.data.request.LoginRequest
import com.capstone.empoweru.data.request.RegisterRequest
import com.capstone.empoweru.data.response.ErrorResponse
import com.capstone.empoweru.data.response.LoginResponse
import com.capstone.empoweru.data.response.RegisterResponse
import com.capstone.empoweru.data.response.UserDataResponse
import com.google.gson.Gson

class AuthRepository {
    suspend fun login(username: String, password: String): LoginResponse? {
        val request = LoginRequest(username, password)
        val response = ApiConfig.apiService.login(request)

        if (response.isSuccessful) {
            return response.body()
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
            // Handle Error Response
            throw ApiException("Login failed: ${errorResponse?.errors?.username}")
        }

        return null
    }

    suspend fun register(username: String, password: String, email: String): RegisterResponse? {
        val request = RegisterRequest(username, password, email)
        val response = ApiConfig.apiService.register(request)

        if (response.isSuccessful) {
            return response.body()
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
            // Handle error response
            throw ApiException("Registration failed: ${errorResponse?.errors?.email ?: errorResponse?.errors?.username}")
        }

        return null
    }

    suspend fun getUserData(id: String): UserDataResponse? {
        val response = ApiConfig.apiService.getUserData(id)

        if (response.isSuccessful) {
            return response.body()
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
            // Handle error response
            throw ApiException("Failed to get user data: ${errorResponse?.error}")
        }

        return null
    }
}

class ApiException(message: String) : Exception(message)