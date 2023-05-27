package com.capstone.empoweru.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.capstone.empoweru.databinding.ActivitySplashBinding
import com.capstone.empoweru.ui.MainActivity
import com.capstone.empoweru.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Delay for 2 seconds (2000 milliseconds) before checking user login status
        Handler(Looper.getMainLooper()).postDelayed({
            checkUserLoggedIn()
        }, 2000)
    }

    private fun checkUserLoggedIn() {
        val isLoggedIn = getUserLoginStatus()

        if (isLoggedIn) {
            // User is logged in, navigate to MainActivity
            navigateToMainActivity()
        } else {
            // User is not logged in, navigate to LoginActivity
            navigateToLoginActivity()
        }
    }

    private fun getUserLoginStatus(): Boolean {
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
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