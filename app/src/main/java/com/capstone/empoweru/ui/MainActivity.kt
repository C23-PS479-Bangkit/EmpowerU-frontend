package com.capstone.empoweru.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.capstone.empoweru.BuildConfig
import com.capstone.empoweru.utils.UserPreferences
import com.google.android.libraries.places.api.Places

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(applicationContext, BuildConfig.PLACES_API)

        val userPreferences = UserPreferences.getInstance(this)

        setContent {
            EmpoweruApp(
                userPreferences = userPreferences,
                context = this
            )
        }

        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            if (selectedImageUri != null) {
                // Later Use
            }
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 123
    }
}