package com.capstone.empoweru.ui.components.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Profile: Screen("profile")
    object Detail: Screen("detail/{umkmId}")
}
