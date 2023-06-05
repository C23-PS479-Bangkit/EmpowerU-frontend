package com.capstone.empoweru.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.capstone.empoweru.data.remote.ApiConfig
import com.capstone.empoweru.data.request.LoginRequest
import com.capstone.empoweru.data.request.UserDataRequest
import com.capstone.empoweru.data.response.ErrorResponse
import com.capstone.empoweru.data.response.LoginResponse
import com.capstone.empoweru.data.response.UserDataResponse
import com.capstone.empoweru.utils.UserPreferences
import com.google.gson.Gson

class LoginRepository(
    private val userPreferences: UserPreferences
) {

    private val userData = MutableLiveData<UserDataResponse>()

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
        Log.d("AuthRepository", "getUserData - Start")
        val request = UserDataRequest(id)
        try {
            val response = ApiConfig.apiService.getUserData(request)

            if (response.isSuccessful) {
                val userDataResponse = response.body()

                userDataResponse?.let {
                    Log.d("UserDataResponse", "Username: ${it.username}")
                    Log.d("UserDataResponse", "Email: ${it.email}")
                }

                userData.value = userDataResponse
                Log.d("AuthRepository", "getUserData - Success")
                return userDataResponse
            } else {
                val errorResponse =
                    Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                // Handle error response
                if (errorResponse != null) {
                    val errorMessage = errorResponse.error
                    if (!errorMessage.isNullOrEmpty()) {
                        throw ApiException("Failed to get user data: $errorMessage")
                    }
                }
                throw ApiException("Failed to get user data: ${response.message()}")
            }
        } catch (e: Exception) {
            throw ApiException("Failed to get user data")
        }
    }

    fun saveUserData(id: String?, username: String?, email: String?) {
        userPreferences.apply {
            this.id = id
            this.username = username
            this.email = email
        }
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        userPreferences.isLoggedIn = isLoggedIn
    }
}

class ApiException(message: String) : Exception(message)