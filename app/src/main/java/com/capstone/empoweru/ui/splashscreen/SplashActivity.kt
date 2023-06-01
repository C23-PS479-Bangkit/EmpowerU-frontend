package com.capstone.empoweru.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.capstone.empoweru.databinding.ActivitySplashBinding
import com.capstone.empoweru.ui.MainActivity
import com.capstone.empoweru.ui.login.LoginActivity
import com.capstone.empoweru.utils.UserPreferences

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreferences = UserPreferences.getInstance(this)
        val viewModelFactory = SplashViewModelFactory(userPreferences)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)

        // Show the image and progress button
        binding.imageSplash.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE

        // Check user login status
        viewModel.checkUserLoginStatus { isLoggedIn ->
            if (isLoggedIn) {
                // User is logged in, navigate to MainActivity
                navigateToMainActivity()
            } else {
                // User is not logged in, navigate to LoginActivity
                navigateToLoginActivity()
            }
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
