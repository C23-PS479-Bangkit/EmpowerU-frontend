package com.capstone.empoweru.data.repository

import com.capstone.empoweru.BuildConfig
import com.capstone.empoweru.data.remote.ApiService
import com.capstone.empoweru.data.response.Location

class LocationRepository(private val apiService: ApiService) {

    suspend fun getListOfLocations(): List<Location> {
        val response = apiService.getListOfLocations()
        if (response.isSuccessful) {
            val result = response.body()
            /*return result?.listLocation ?: emptyList()*/

            // Image uses API_KEY from PLACES_API
            val locations = result?.listLocation ?: emptyList()
            return locations.map { location ->
                val imageUrl = addApiKeyToImageUrl(location.urlPhoto)
                location.copy(urlPhoto = imageUrl)
            }
        } else {
            throw ApiException("Failed to fetch locations")
        }
    }

    // Image uses API_KEY from PLACES_API
    private fun addApiKeyToImageUrl(imageUrl: String): String {
        val apiKey = BuildConfig.PLACES_API
        return imageUrl.replace("INSERT_GMAPS_API_KEY", apiKey)
    }
}
