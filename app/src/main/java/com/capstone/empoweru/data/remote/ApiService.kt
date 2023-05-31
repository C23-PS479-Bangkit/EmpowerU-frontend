package com.capstone.empoweru.data.remote

import com.capstone.empoweru.data.request.CreateLocationRequest
import com.capstone.empoweru.data.request.LoginRequest
import com.capstone.empoweru.data.request.RegisterRequest
import com.capstone.empoweru.data.request.UserDataRequest
import com.capstone.empoweru.data.response.CreateLocationResponse
import com.capstone.empoweru.data.response.LoginResponse
import com.capstone.empoweru.data.response.RegisterResponse
import com.capstone.empoweru.data.response.UserDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("signup")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("datauser")
    suspend fun getUserData(@Body request: UserDataRequest): Response<UserDataResponse>

    @POST("create_location")
    suspend fun createLocation(@Body request: CreateLocationRequest): Response<CreateLocationResponse>
}