package com.capstone.empoweru.data.repository

import com.capstone.empoweru.data.remote.ApiConfig
import com.capstone.empoweru.data.request.RegisterRequest
import com.capstone.empoweru.data.response.ErrorResponse
import com.capstone.empoweru.data.response.RegisterResponse
import com.google.gson.Gson

class RegisterRepository {

    suspend fun register(username: String, password: String, email: String): RegisterResponse? {
        val request = RegisterRequest(username, password, email)
        val response = ApiConfig.apiService.register(request)

        if (response.isSuccessful) {
            return response.body()
        } else {
            val errorResponse =
                Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
            // Handle error response
            if (errorResponse != null) {
                val usernameError = errorResponse.errors?.usernameError
                val passwordError = errorResponse.errors?.passwordError
                val emailError = errorResponse.errors?.emailError

                if (!usernameError.isNullOrEmpty()) {
                    throw ApiException("Registration failed: $usernameError")
                } else if (!emailError.isNullOrEmpty()) {
                    throw ApiException("Registration failed: $emailError")
                } else if (!passwordError.isNullOrEmpty()) {
                    throw ApiException("Registration failed: $passwordError")
                } else {
                    throw ApiException("Registration failed")
                }
            } else {
                throw ApiException("Registration failed: ${response.message()}")
            }
        }

        return null
    }
}