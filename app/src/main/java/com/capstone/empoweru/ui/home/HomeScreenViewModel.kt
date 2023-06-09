package com.capstone.empoweru.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.empoweru.data.dummy.CategoryItem
import com.capstone.empoweru.data.repository.LocationRepository
import com.capstone.empoweru.data.response.Location
import com.capstone.empoweru.utils.UserPreferences
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val userPreferences: UserPreferences,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _locations = mutableStateOf<List<Location>>(emptyList())
    val locations: State<List<Location>> = _locations

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _selectedLocation = mutableStateOf<Location?>(null)
    val selectedLocation: State<Location?> = _selectedLocation

    private val _selectedCategory = mutableStateOf<CategoryItem?>(null)
    val selectedCategory: State<CategoryItem?> = _selectedCategory

    fun selectLocation(location: Location) {
        _selectedLocation.value = location
    }

    fun selectCategory(category: CategoryItem) {
        _selectedCategory.value = category
    }

    fun getUsername(): String {
        return userPreferences.username ?: ""
    }

    fun fetchLocations() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val allLocations = locationRepository.getListOfLocations()
                val filteredLocations = when (selectedCategory.value) {
                    CategoryItem.TRENDING -> allLocations.sortedByDescending { it.rating }
                    CategoryItem.FASHION -> allLocations.filter { it.type == "fashion" }
                    CategoryItem.FOOD -> allLocations.filter { it.type == "restaurant" || it.type == "food" }
                    CategoryItem.SHOP -> allLocations.filter { it.type == "store" }
                    CategoryItem.BUSINESS -> allLocations.filter { it.type == "business" }
                    CategoryItem.OTHER -> allLocations.filter { location ->
                        location.type !in listOf("fashion", "restaurant", "food", "store", "business")
                    }
                    else -> allLocations
                }
                _locations.value = filteredLocations
            } catch (e: Exception) {
                // Handle the exception, e.g., show an error message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
