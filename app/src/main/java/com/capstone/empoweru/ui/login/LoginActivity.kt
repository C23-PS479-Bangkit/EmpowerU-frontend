package com.capstone.empoweru.ui.login

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.empoweru.R
import com.capstone.empoweru.databinding.ActivityLoginBinding
import com.capstone.empoweru.ui.MainActivity
import com.capstone.empoweru.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Set up click listeners
        binding.btnLogin.setOnClickListener {
            val username = binding.edLoginName.text.toString()
            val password = binding.edLoginPassword.text.toString()
            binding.btnLogin.setLoading(true)
            viewModel.login(username, password)
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