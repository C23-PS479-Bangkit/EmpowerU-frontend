package com.capstone.empoweru.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.empoweru.databinding.ActivityRegisterBinding
import com.capstone.empoweru.ui.login.LoginActivity
import com.capstone.empoweru.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialAlpha()
        playAnimation()

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        setupListeners()

        binding.tvClickHaveAccount.setOnClickListener {
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_left, R.anim.slide_out_right)
            startActivity(
                Intent(this, LoginActivity::class.java), options.toBundle()
            )
            finish()
        }
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val username = binding.edRegisterName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()

            if (binding.edRegisterPassword.isValid()) {
                registerUser(username, email, password)
            }
        }
    }

    private fun registerUser(username: String, email: String, password: String) {
        binding.btnRegister.setLoading(true)

        viewModel.register(username, password, email).observe(this) { response ->
            binding.btnRegister.setLoading(false)

            if (response != null) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                val options = ActivityOptions.makeCustomAnimation(this@RegisterActivity, R.anim.slide_in_left, R.anim.slide_out_right)
                startActivity(
                    Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), options.toBundle()
                )
                finish()
            } else {
                val errorMessage = viewModel.errorMessage
                if (!errorMessage.isNullOrEmpty()) {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.tvHello, View.ALPHA, 1f).setDuration(500)
        val appName = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1f).setDuration(500)
        val info = ObjectAnimator.ofFloat(binding.tvCreateAccount, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(500)
        val edEmail = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val edPassword = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val button = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val haveAccount = ObjectAnimator.ofFloat(binding.tvHaveAccount, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, appName, info, name, edEmail, edPassword, button, haveAccount)
            startDelay = 500
            start()
        }
    }
    private fun initialAlpha() {
        binding.tvHello.alpha = 0f
        binding.tvAppName.alpha = 0f
        binding.tvCreateAccount.alpha = 0f
        binding.edRegisterName.alpha = 0f
        binding.edRegisterEmail.alpha = 0f
        binding.edRegisterPassword.alpha = 0f
        binding.btnRegister.alpha = 0f
        binding.tvHaveAccount.alpha = 0f
    }
}