package com.capstone.empoweru.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.ui.components.navigation.NavigationItem
import com.capstone.empoweru.ui.components.navigation.Screen
import com.capstone.empoweru.ui.home.HomeScreen
import com.capstone.empoweru.ui.profile.ProfileScreen
import com.capstone.empoweru.ui.profile.ProfileViewModel
import com.capstone.empoweru.ui.theme.BottomBarTheme
import com.capstone.empoweru.ui.theme.EmpowerUTheme

@Composable
fun EmpoweruApp(
    profileViewModel: ProfileViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    EmpowerUTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                bottomBar = {
                    BottomBar(navController)
                },
                modifier = modifier
            ) { innerpadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.padding(innerpadding)
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen()
                    }

                    composable(Screen.Profile.route) {
                        ProfileScreen(profileViewModel)
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomBarTheme {
        BottomNavigation(
            modifier = modifier
        ) {
            val navigationItems = listOf(
                NavigationItem(
                    title = "Home",
                    icon = Icons.Default.Home,
                    screen = Screen.Home
                ),

                NavigationItem(
                    title = "Profile",
                    icon = Icons.Default.AccountCircle,
                    screen = Screen.Profile
                )
            )

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            BottomNavigation() {
                navigationItems.map { item ->

                    val isSelected = currentRoute == item.screen.route
                    val alpha = if (isSelected) 1f else 0.5f

                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = Color.Red.copy(alpha = alpha)
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.button,
                                color = Color.Red.copy(alpha = alpha)
                            )
                        },
                        selected = currentRoute == item.screen.route,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                restoreState = true
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmpoweruAppPreview() {
    EmpoweruApp(profileViewModel = ProfileViewModel())
}