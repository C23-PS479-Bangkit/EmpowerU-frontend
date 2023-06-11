package com.capstone.empoweru.ui.splashscreen

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.empoweru.R
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
        val viewModelFactory = SplashViewModelFactory(this, userPreferences)
        viewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]

        val fadeInAnimation = AlphaAnimation(0f, 1f)
        fadeInAnimation.duration = 1000
        binding.tvAppDesc.startAnimation(fadeInAnimation)


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
        val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
        startActivity(
            Intent(this, MainActivity::class.java), options.toBundle()
        )
        finish()
    }

    private fun navigateToLoginActivity() {
        val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
        startActivity(
            Intent(this, LoginActivity::class.java), options.toBundle()
        )
        finish()
    }
}
