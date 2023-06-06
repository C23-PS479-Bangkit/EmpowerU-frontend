package com.capstone.empoweru.data.response

data class LocationResponse(
    val status: Int,
    val listLocation: List<Location>
)

data class Location(
    val address: String,
    val name: String,
    val type: String,
    val rating: Double,
    val GMapsID: String,
    val impression: String,
    val urlPhoto: String
)
