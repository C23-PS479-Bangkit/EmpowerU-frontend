package com.capstone.empoweru.data.request

import com.google.gson.annotations.SerializedName

data class CreateLocationRequest(
    @SerializedName("gmapsID") val gmapsID: String
)
