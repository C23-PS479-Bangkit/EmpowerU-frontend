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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.data.dummy.CategoryItem
import com.capstone.empoweru.data.dummy.Umkm
import com.capstone.empoweru.data.dummy.dummyUmkm
import com.capstone.empoweru.data.dummy.getCategory
import com.capstone.empoweru.ui.components.CategoryButton
import com.capstone.empoweru.ui.components.HeaderCard
import com.capstone.empoweru.ui.components.SearchBar
import com.capstone.empoweru.ui.components.UmkmCard
import com.capstone.empoweru.ui.components.navigation.Screen
import com.capstone.empoweru.ui.theme.EmpowerUTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddPlaces.route) },
                backgroundColor = Color.Red,
                modifier = Modifier
                    .padding(bottom = 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
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
                modifier = Modifier.
                padding(top = 8.dp)
            )
            SearchBar(
                modifier = Modifier.fillMaxWidth()
            )
            CategoryRow(
                modifier = Modifier
            )
            UmkmList(
                modifier = Modifier,
                onItemClick = { umkm ->
                    navController.navigate("${Screen.Detail.route}/${umkm.title}")
                }
            )
        }
    }
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier
) {
    val categoryList = getCategory()
    var selectedCategory by remember { mutableStateOf<CategoryItem?>(null) }
    
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categoryList) { category -> 
            CategoryButton(
                category = category,
                selectedCategory = selectedCategory,
                onCategorySelected = { newCategory ->
                    selectedCategory = newCategory
                }
            )
        }
    }
}

@Composable
fun UmkmList(
    modifier: Modifier = Modifier,
    onItemClick: (Umkm) -> Unit
) {
    val umkmList = dummyUmkm

    LazyColumn {
        items(umkmList) { umkm -> 
            UmkmCard(umkm = umkm, onClick = { onItemClick(umkm) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    EmpowerUTheme() {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}