package com.capstone.empoweru.ui.addplaces

import android.app.Application
import androidx.lifecycle.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient

class AddPlaceViewModel(application: Application) : AndroidViewModel(application) {
    private val placesClient: PlacesClient = Places.createClient(application)

    private val _searchResults = MutableLiveData<List<AutocompletePrediction>>()
    val searchResults: LiveData<List<AutocompletePrediction>> = _searchResults

    fun searchPlaces(query: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
            .setSessionToken(AutocompleteSessionToken.newInstance())
            .setCountries("IDN")
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                _searchResults.value = response.autocompletePredictions
            }
            .addOnFailureListener { exception: Exception ->
                // Handle error
            }
    }
}

@Suppress("UNCHECKED_CAST")
class PlacesViewModellFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddPlaceViewModel::class.java) -> {
                AddPlaceViewModel(application) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}