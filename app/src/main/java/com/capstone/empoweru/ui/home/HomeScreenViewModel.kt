package com.capstone.empoweru.ui.home

import androidx.lifecycle.ViewModel
import com.capstone.empoweru.utils.UserPreferences

class HomeScreenViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    fun getUsername(): String {
        return userPreferences.username ?: ""
    }
}