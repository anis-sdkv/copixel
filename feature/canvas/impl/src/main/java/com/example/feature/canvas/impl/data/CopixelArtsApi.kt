package com.example.feature.canvas.impl.data

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface CopixelArtsApi {
    @Multipart
    @POST("arts/create")
    suspend fun create(
        @Part("image") image: RequestBody
    ): Response<Unit>

    @GET("arts")
    suspend fun getByUser(@Query("id") userId: String): Response<Unit>
}