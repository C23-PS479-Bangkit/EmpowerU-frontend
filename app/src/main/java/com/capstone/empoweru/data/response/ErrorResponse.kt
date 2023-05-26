package com.capstone.empoweru.data.response

data class ErrorResponse(
    val errors: ErrorDetails?,
    val error: String?,
    val status: Int
)

data class ErrorDetails(
    val username: String?,
    val password: String?,
    val email: String?
)
