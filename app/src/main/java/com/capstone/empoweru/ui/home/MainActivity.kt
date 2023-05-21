package com.capstone.empoweru.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.empoweru.R
import com.capstone.empoweru.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}