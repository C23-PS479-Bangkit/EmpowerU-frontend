package com.capstone.empoweru.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.empoweru.data.repository.LoginRepository
import com.capstone.empoweru.databinding.ActivityLoginBinding
import com.capstone.empoweru.ui.MainActivity
import com.capstone.empoweru.ui.ViewModelFactory
import com.capstone.empoweru.ui.register.RegisterActivity
import com.capstone.empoweru.utils.UserPreferences

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreferences = UserPreferences.getInstance(applicationContext)
        val repository = LoginRepository(userPreferences)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(applicationContext, repository, userPreferences)
        ).get(LoginViewModel::class.java)

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

        // Observe login result
        viewModel.loginResult.observe(this) { result ->
            result?.let {
                if (it.success) {
                    // Login successful, navigate to MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    binding.btnLogin.setLoading(false)
                    // Login failed, display error message
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvClickHaveAccount.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
            finish()
        }
    }
}