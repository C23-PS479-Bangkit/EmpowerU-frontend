package com.capstone.empoweru.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.empoweru.data.repository.ApiException
import com.capstone.empoweru.data.repository.AuthRepository
import com.capstone.empoweru.data.response.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context): ViewModel() {

    private val repository = AuthRepository()

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                if (response != null) {
                    // Login successful
                    saveLoginStatus(true)
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
                _loginResult.value = LoginResult(success = false, error = "An error occurred")
            }
        }
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }
}