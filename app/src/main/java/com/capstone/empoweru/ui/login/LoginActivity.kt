package com.capstone.empoweru.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.empoweru.R
import com.capstone.empoweru.data.repository.LoginRepository
import com.capstone.empoweru.databinding.ActivityLoginBinding
import com.capstone.empoweru.ui.MainActivity
import com.capstone.empoweru.ui.register.RegisterActivity
import com.capstone.empoweru.data.local.UserPreferences

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialAlpha()
        playAnimation()

        val userPreferences = UserPreferences.getInstance(applicationContext)
        val repository = LoginRepository(userPreferences)

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(repository)
        )[LoginViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            val username = binding.edLoginName.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.edLoginPassword.isValid()) {
                    binding.btnLogin.setLoading(true)
                    viewModel.login(username, password)
                }
            }
        }

        viewModel.loginResult.observe(this) { result ->
            result?.let {
                if (it.success) {
                    val options = ActivityOptions.makeCustomAnimation(this@LoginActivity, R.anim.slide_in_right, R.anim.slide_out_left)
                    startActivity(Intent(
                        this, MainActivity::class.java), options.toBundle()
                    )
                    finish()
                } else {
                    binding.btnLogin.setLoading(false)
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvClickHaveAccount.setOnClickListener {
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(
                Intent(this, RegisterActivity::class.java), options.toBundle()
            )
            finish()
        }
    }
    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.tvHello, View.ALPHA, 1f).setDuration(500)
        val appName = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1f).setDuration(500)
        val info = ObjectAnimator.ofFloat(binding.tvLoginAccount, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.edLoginName, View.ALPHA, 1f).setDuration(500)
        val edPassword = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(500)
        val button = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val noAccount = ObjectAnimator.ofFloat(binding.tvNoAccount, View.ALPHA, 1f).setDuration(500)
        val clickAccount = ObjectAnimator.ofFloat(binding.tvClickHaveAccount, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, appName, info, name, edPassword, button, noAccount, clickAccount)
            startDelay = 500
            start()
        }
    }
    private fun initialAlpha() {
        binding.tvHello.alpha = 0f
        binding.tvAppName.alpha = 0f
        binding.tvLoginAccount.alpha = 0f
        binding.edLoginName.alpha = 0f
        binding.edLoginPassword.alpha = 0f
        binding.btnLogin.alpha = 0f
        binding.tvNoAccount.alpha = 0f
        binding.tvClickHaveAccount.alpha = 0f
    }
}