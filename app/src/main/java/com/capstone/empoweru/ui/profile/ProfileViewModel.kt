package com.capstone.empoweru.ui.profile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.capstone.empoweru.ui.login.LoginActivity

class ProfileViewModel : ViewModel() {

    fun logout(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("isLoggedIn")
        editor.apply()

        // You can perform any additional logout logic here, such as navigating to the LoginActivity

        // Example: Navigating to LoginActivity
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}
