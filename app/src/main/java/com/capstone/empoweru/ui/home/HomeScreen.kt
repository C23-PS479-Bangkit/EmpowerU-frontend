package com.capstone.empoweru.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.data.dummy.getCategory
import com.capstone.empoweru.data.remote.ApiConfig
import com.capstone.empoweru.data.repository.ListCommentRepository
import com.capstone.empoweru.data.repository.LocationRepository
import com.capstone.empoweru.ui.components.CategoryButton
import com.capstone.empoweru.ui.components.HeaderCard
import com.capstone.empoweru.ui.components.SearchBar
import com.capstone.empoweru.ui.components.UmkmCard
import com.capstone.empoweru.ui.components.navigation.Screen
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import com.capstone.empoweru.utils.UserPreferences


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel
) {

    val systemWindowBackground = MaterialTheme.colors.background

    val username = homeScreenViewModel.getUsername()
    val locations = homeScreenViewModel.locations.value
    val isLoading = homeScreenViewModel.isLoading.value

    var searchQuery by remember { mutableStateOf("") }
    val filteredLocations = locations.filter { location ->
        location.name.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        homeScreenViewModel.fetchLocations()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddPlaces.route) },
                backgroundColor = Color.Red,
                modifier = Modifier
                    .padding(bottom = 52.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddLocationAlt,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier
                        .size(28.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = rememberScaffoldState(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 52.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    HeaderCard(
                        username = username,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(systemWindowBackground)
                            .padding(top = 8.dp)
                    ) {
                        Column {
                            SearchBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp),
                                onSearch = { query -> searchQuery = query }
                            )
                            CategoryRow(homeScreenViewModel)
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }

                items(filteredLocations) { location ->
                    UmkmCard(location) {
                        homeScreenViewModel.selectLocation(location)
                        navController.navigate("${Screen.Detail.route}/${location.name}") {
                            launchSingleTop = true
                        }
                    }
                }
            }

            if (isLoading && filteredLocations.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun CategoryRow(
    homeScreenViewModel: HomeScreenViewModel
) {
    val categoryList = getCategory()

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categoryList) { category ->
            CategoryButton(
                category = category,
                selectedCategory = homeScreenViewModel.selectedCategory.value,
                onCategorySelected = { newCategory ->
                    homeScreenViewModel.selectCategory(newCategory)
                    homeScreenViewModel.fetchLocations()
                }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    EmpowerUTheme() {
        val userPreferences = UserPreferences.getInstance(LocalContext.current)
        val apiService = ApiConfig.apiService
        val locationRepository = LocationRepository(apiService)
        val listCommentRepository = ListCommentRepository()

        val viewModel = HomeScreenViewModel(userPreferences, locationRepository, listCommentRepository)

        val navController = rememberNavController()
        HomeScreen(navController = navController, viewModel)
    }
}