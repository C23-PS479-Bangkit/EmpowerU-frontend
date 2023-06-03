package com.capstone.empoweru.data.repository

import com.capstone.empoweru.data.remote.ApiService
import com.capstone.empoweru.data.response.Location

class LocationRepository(private val apiService: ApiService) {

    suspend fun getListOfLocations(): List<Location> {
        val response = apiService.getListOfLocations()
        if (response.isSuccessful) {
            val result = response.body()
            return result?.listLocation ?: emptyList()
        } else {
            throw ApiException("Failed to fetch locations")
        }
    }
}
