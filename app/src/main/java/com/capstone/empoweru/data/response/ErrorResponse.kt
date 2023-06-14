package com.capstone.empoweru.data.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    val errors: ErrorDetails?,
    val error: String?,
    val status: Int
)

data class ErrorDetails(
    @SerializedName("username")
    val usernameError: String?,

    @SerializedName("password")
    val passwordError: String?,

    @SerializedName("email")
    val emailError: String?
)
