package com.capstone.empoweru.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.R
import com.capstone.empoweru.data.dummy.CategoryItem
import com.capstone.empoweru.data.dummy.getCategory
import com.capstone.empoweru.data.remote.ApiConfig
import com.capstone.empoweru.data.repository.LocationRepository
import com.capstone.empoweru.data.response.Location
import com.capstone.empoweru.ui.components.CategoryButton
import com.capstone.empoweru.ui.components.HeaderCard
import com.capstone.empoweru.ui.components.SearchBar
import com.capstone.empoweru.ui.components.UmkmCard
import com.capstone.empoweru.ui.components.navigation.Screen
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import com.capstone.empoweru.utils.UserPreferences


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
) {

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
                    painter = painterResource(R.drawable.ic_add_location),
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = rememberScaffoldState(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 52.dp)
        ) {
            HeaderCard(
                username = username,
                modifier = Modifier.
                padding(top = 8.dp)
            )
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                onSearch = { query -> searchQuery = query }
            )
            CategoryRow(
                homeScreenViewModel,
                modifier = Modifier
            )
            UmkmList(
                modifier = Modifier,
                onItemClick = { location ->
                    homeScreenViewModel.selectLocation(location)
                    navController.navigate("${Screen.Detail.route}/${location.name}") {
                        launchSingleTop = true
                    }
                },
                locations = filteredLocations,
                isLoading = isLoading
            )
        }
    }
}

@Composable
fun CategoryRow(
    homeScreenViewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
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


@Composable
fun UmkmList(
    modifier: Modifier = Modifier,
    onItemClick: (Location) -> Unit,
    locations: List<Location>,
    isLoading: Boolean
) {
    LazyColumn(modifier) {
        items(locations) { location ->
            UmkmCard(location, onClick = { onItemClick(location) })
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            }
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

        val viewModel = HomeScreenViewModel(userPreferences, locationRepository)

        val navController = rememberNavController()
        HomeScreen(navController = navController, viewModel)
    }
}