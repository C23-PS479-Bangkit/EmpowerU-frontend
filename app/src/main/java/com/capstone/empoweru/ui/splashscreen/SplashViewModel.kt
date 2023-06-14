package com.capstone.empoweru.ui.splashscreen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.capstone.empoweru.data.local.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(private val context: Context, private val userPreferences: UserPreferences) : ViewModel() {

    fun checkUserLoginStatus(onComplete: (isLoggedIn: Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            val isLoggedIn = userPreferences.isLoggedIn

            if (isConnectedToInternet()) {
                delay(2000) // Add the delay here if needed
                withContext(Dispatchers.Main) {
                    onComplete(isLoggedIn)
                }
            } else {
                withContext(Dispatchers.Main) {
                    showToast("Silahkan hubungkan ke Internet terlebih dahulu")
                }
            }
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

class SplashViewModelFactory(
    private val context: Context,
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(context, userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

