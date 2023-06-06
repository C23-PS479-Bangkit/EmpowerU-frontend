package com.capstone.empoweru.data.remote

import com.capstone.empoweru.data.request.*
import com.capstone.empoweru.data.response.*
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

    @GET("get_list_location")
    suspend fun getListOfLocations(): Response<LocationResponse>

    @POST("create_comment")
    suspend fun createComment(@Body request: CommentRequest): CommentResponse

    @GET("get_list_comment")
    suspend fun getListOfComments(@Query("GMapsID") gmapsId: String): Response<CommentListResponse>
}