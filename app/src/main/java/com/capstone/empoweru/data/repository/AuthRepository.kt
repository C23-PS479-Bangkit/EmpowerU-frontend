package com.capstone.empoweru.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.empoweru.data.remote.ApiConfig
import com.capstone.empoweru.data.request.LoginRequest
import com.capstone.empoweru.data.request.RegisterRequest
import com.capstone.empoweru.data.request.UserDataRequest
import com.capstone.empoweru.data.response.ErrorResponse
import com.capstone.empoweru.data.response.LoginResponse
import com.capstone.empoweru.data.response.RegisterResponse
import com.capstone.empoweru.data.response.UserDataResponse
import com.google.gson.Gson

class AuthRepository {

    private val userData = MutableLiveData<UserDataResponse>()

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

    suspend fun login(username: String, password: String): LoginResponse? {
        val request = LoginRequest(username, password)
        val response = ApiConfig.apiService.login(request)

        if (response.isSuccessful) {
            return response.body()
        } else {
            val errorResponse =
                Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
            // Handle Error Response
            if (errorResponse != null) {
                val usernameError = errorResponse.errors?.usernameError

                if (!usernameError.isNullOrEmpty()) {
                    throw ApiException("Login failed: $usernameError")
                } else {
                    throw ApiException("Login failed")
                }

            } else {
                throw ApiException("Login failed: ${response.message()}")
            }
        }

        return null
    }

    suspend fun getUserData(id: String): UserDataResponse? {
        val request = UserDataRequest(id)
        val response = ApiConfig.apiService.getUserData(request)

        if (response.isSuccessful) {
            val userDataResponse = response.body()
            userData.value = userDataResponse
            return userDataResponse
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
            // Handle error response
            if (errorResponse != null) {
                val errorMessage = errorResponse.error
                if (!errorMessage.isNullOrEmpty()) {
                    throw ApiException("Failed to get user data: $errorMessage")
                }
            }
            throw ApiException("Failed to get user data: ${response.message()}")
        }
    }


    fun getUserData(): LiveData<UserDataResponse> {
        return userData
    }
}

class ApiException(message: String) : Exception(message)