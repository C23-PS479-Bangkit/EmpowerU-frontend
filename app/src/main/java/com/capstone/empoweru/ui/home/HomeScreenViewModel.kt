package com.capstone.empoweru.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun selectLocation(location: Location) {
        _selectedLocation.value = location
    }

    fun getUsername(): String {
        return userPreferences.username ?: ""
    }

    fun fetchLocations() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _locations.value = locationRepository.getListOfLocations()
            } catch (e: Exception) {
                // Handle the exception, e.g., show an error message
            } finally {
                _isLoading.value = false
            }
        }
    }
}