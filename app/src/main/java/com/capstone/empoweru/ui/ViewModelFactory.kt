package com.capstone.empoweru.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.empoweru.data.repository.LoginRepository
import com.capstone.empoweru.ui.login.LoginViewModel
import com.capstone.empoweru.utils.UserPreferences

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val context: Context,
    private val repository: LoginRepository,
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(context, repository, userPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}