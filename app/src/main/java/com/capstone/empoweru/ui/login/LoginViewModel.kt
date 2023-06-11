package com.capstone.empoweru.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.empoweru.data.repository.ApiException
import com.capstone.empoweru.data.repository.LoginRepository
import com.capstone.empoweru.data.response.LoginResult
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider

class LoginViewModel(
    private val repository: LoginRepository,
    ): ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

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

                    repository.saveUserData(id, username, email)
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

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val repository: LoginRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}