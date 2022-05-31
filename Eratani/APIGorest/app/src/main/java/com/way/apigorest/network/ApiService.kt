package com.way.apigorest.network

import com.way.apigorest.BuildConfig
import com.way.apigorest.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("users")
    suspend fun getUser(): Response<List<User>>

    @FormUrlEncoded
    @Headers(
        "Authorization:Bearer ${BuildConfig.Token}"
    )
    @POST("users")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("gender") gender: String,
        @Field("status") status: String
    ): Response<User>
}