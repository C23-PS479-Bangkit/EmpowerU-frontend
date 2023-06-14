package com.capstone.empoweru.data.request

import com.google.gson.annotations.SerializedName

data class CreateLocationRequest(
    @SerializedName("GMapsID") val GMapsID: String
)
