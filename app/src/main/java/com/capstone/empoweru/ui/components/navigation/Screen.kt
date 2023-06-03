package com.capstone.empoweru.ui.components.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Profile: Screen("profile")
    object Detail: Screen("detail")
    object Review : Screen("review")
    object AddPlaces : Screen("addPlaces")
}
