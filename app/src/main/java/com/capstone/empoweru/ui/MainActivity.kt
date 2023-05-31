package com.capstone.empoweru.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.empoweru.BuildConfig
import com.capstone.empoweru.ui.profile.ProfileViewModel
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {

    private val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(applicationContext, BuildConfig.PLACES_API)
        setContent {
            EmpoweruApp(profileViewModel)
        }

        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}