package com.capstone.empoweru.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.empoweru.data.repository.ApiException
import com.capstone.empoweru.data.repository.LoginRepository
import com.capstone.empoweru.data.response.LoginResult
import com.capstone.empoweru.data.response.UserDataResponse
import com.capstone.empoweru.utils.UserPreferences
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context,
    private val repository: LoginRepository,
    private val userPreferences: UserPreferences
    ): ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _userData = MutableLiveData<UserDataResponse>()
    val userData: LiveData<UserDataResponse> = _userData

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                if (response != null) {
                    // Login successful
                    val id = response.user
                    Log.d("LoginViewModel", "Received user ID: $id")

                    val userDataResponse = repository.getUserData(id)
                    val username = userDataResponse?.username
                    val email = userDataResponse?.email

                    repository.saveUserData(username, email)
                    repository.saveLoginStatus(true)

                    _loginResult.value = LoginResult(success = true)
                } else {
                    // Login failed
                    _loginResult.value = LoginResult(success = false, error = "Login failed")
                }
            } catch (e: ApiException) {
                // Handle API exception
                _loginResult.value = LoginResult(success = false, error = e.message)
            } catch (e: Exception) {
                // Handle other exceptions
                val errorMessage = e.message
                if (!errorMessage.isNullOrEmpty()) {
                    _loginResult.value = LoginResult(success = false, error = errorMessage)
                } else {
                    _loginResult.value = LoginResult(success = false, error = "Login failed")
                }
            }
        }
    }
}