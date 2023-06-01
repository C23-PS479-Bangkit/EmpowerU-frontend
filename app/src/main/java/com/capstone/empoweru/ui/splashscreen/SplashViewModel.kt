package com.capstone.empoweru.ui.splashscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.capstone.empoweru.utils.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    fun checkUserLoginStatus(onComplete: (isLoggedIn: Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            delay(2000)

            val isLoggedIn = userPreferences.isLoggedIn

            onComplete(isLoggedIn)
        }
    }
}

class SplashViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

