package com.capstone.empoweru.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.capstone.empoweru.data.remote.ApiConfig
import com.capstone.empoweru.data.repository.ListCommentRepository
import com.capstone.empoweru.data.repository.LocationRepository
import com.capstone.empoweru.data.response.Location
import com.capstone.empoweru.ui.addplaces.AddPlaceScreen
import com.capstone.empoweru.ui.components.navigation.NavigationItem
import com.capstone.empoweru.ui.components.navigation.Screen
import com.capstone.empoweru.ui.detail.DetailScreen
import com.capstone.empoweru.ui.home.HomeScreen
import com.capstone.empoweru.ui.home.HomeScreenViewModel
import com.capstone.empoweru.ui.profile.ProfileScreen
import com.capstone.empoweru.ui.profile.ProfileViewModel
import com.capstone.empoweru.ui.profile.ProfileViewModelFactory
import com.capstone.empoweru.ui.review.ReviewScreen
import com.capstone.empoweru.ui.theme.BottomBarTheme
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import com.capstone.empoweru.utils.UserPreferences

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EmpoweruApp(
    userPreferences: UserPreferences,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    var locations by remember { mutableStateOf(emptyList<Location>()) }

    val apiService = ApiConfig.apiService
    val locationRepository = LocationRepository(apiService)
    val listCommentRepository = ListCommentRepository()

    val homeScreenViewModel = HomeScreenViewModel(userPreferences, locationRepository, listCommentRepository)
    val profileViewModel = viewModel<ProfileViewModel>(factory = ProfileViewModelFactory(userPreferences))

    LaunchedEffect(Unit) {
        locations =  locationRepository.getListOfLocations()
    }

    EmpowerUTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = modifier
            ) {
                composable(Screen.Home.route) {
                    Scaffold(
                        bottomBar = {
                            BottomBar(navController)
                        },
                        modifier = Modifier
                    ) {
                        HomeScreen(navController, homeScreenViewModel)
                    }
                }

                composable(
                    route = "${Screen.Detail.route}/{name}",
                    arguments = listOf(navArgument("name") { type = NavType.StringType }),
                ) { backStackEntry ->
                    val arguments = requireNotNull(backStackEntry.arguments)
                    val name = arguments.getString("name")
                    val selectedLocation = homeScreenViewModel.selectedLocation.value
                    val location = if (selectedLocation?.name == name) {
                        selectedLocation
                    } else {
                        locations.find { it.name == name }
                    }
                    if (location != null) {
                        DetailScreen(location, navController)
                    } else {
                        // Handle the case where the location is not found
                    }
                }


                composable(Screen.Profile.route) {
                    Scaffold(
                        bottomBar = {
                            BottomBar(navController)
                        },
                        modifier = Modifier
                    ) {
                        ProfileScreen(navController, profileViewModel)
                    }
                }

                composable(Screen.Review.route) {
                    val location = homeScreenViewModel.selectedLocation.value
                    if (location != null) {
                        ReviewScreen(navController, location)
                    }
                }

                composable(Screen.AddPlaces.route) {
                    AddPlaceScreen(navController)
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

            BottomNavigation {
                navigationItems.map { item ->

                    val isSelected = currentRoute == item.screen.route
                    val alpha = if (isSelected) 1f else 0.25f

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

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun EmpoweruAppPreview() {
    val userPreferences = UserPreferences.getInstance(LocalContext.current)

    EmpoweruApp(
        userPreferences = userPreferences,
    )
}