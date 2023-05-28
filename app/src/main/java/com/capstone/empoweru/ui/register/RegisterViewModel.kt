package com.capstone.empoweru.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.empoweru.data.repository.ApiException
import com.capstone.empoweru.data.repository.AuthRepository
import com.capstone.empoweru.data.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private val repository = AuthRepository()
    var errorMessage: String? = null

    fun register(username: String, password: String, email: String): LiveData<RegisterResponse?> {
        val registerResponse = MutableLiveData<RegisterResponse?>()

        viewModelScope.launch {
            try {
                val response = repository.register(username, password, email)
                registerResponse.value = response
            } catch (e: ApiException) {
                // Handle Exception
                errorMessage = e.message
                registerResponse.value = null
            }
        }

        return registerResponse
    }
}