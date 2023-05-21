package com.capstone.empoweru.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.empoweru.R
import com.capstone.empoweru.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}