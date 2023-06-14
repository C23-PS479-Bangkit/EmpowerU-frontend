package com.capstone.empoweru.ui.addplaces

import android.app.Application
import androidx.lifecycle.*
import com.capstone.empoweru.data.remote.ApiConfig
import com.capstone.empoweru.data.request.CreateLocationRequest
import com.capstone.empoweru.data.response.CreateLocationResponse
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.launch
import retrofit2.Response

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
                val filteredPredictions = response.autocompletePredictions.filter { prediction ->
                    val placeTypes = prediction.placeTypes
                    val umkmTypes = listOf(
                        Place.Type.RESTAURANT,
                        Place.Type.STORE,
                        Place.Type.CAFE,
                        Place.Type.CLOTHING_STORE,
                        Place.Type.CONVENIENCE_STORE,
                        Place.Type.BAKERY,
                        Place.Type.CAR_WASH,
                        Place.Type.HARDWARE_STORE,
                        Place.Type.FURNITURE_STORE,
                        Place.Type.DRUGSTORE,
                        Place.Type.FLORIST,
                        Place.Type.LAUNDRY,
                        Place.Type.PET_STORE,
                        Place.Type.PHARMACY,
                        Place.Type.PLUMBER,
                        Place.Type.VETERINARY_CARE
                    )

                    placeTypes.intersect(umkmTypes).isNotEmpty()
                }

                _searchResults.value = filteredPredictions
            }
            .addOnFailureListener { exception: Exception ->
                // Handle error
            }
    }

    fun createLocation(request: CreateLocationRequest, onComplete: (Response<CreateLocationResponse>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.apiService.createLocation(request)
                onComplete(response)
            } catch (e: Exception) {
                // Handle API call exception
                // You can show an error message or perform any necessary action
            }
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