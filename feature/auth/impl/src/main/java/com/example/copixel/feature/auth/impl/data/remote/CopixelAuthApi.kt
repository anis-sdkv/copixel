package com.example.copixel.feature.auth.impl.data.remote

import com.example.copixel.feature.auth.impl.data.remote.request.LoginRequest
import com.example.copixel.feature.auth.impl.data.remote.request.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CopixelAuthApi {
    @POST("accounts/register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>
    @POST("accounts/login")
    suspend fun login(@Body request: LoginRequest): Response<Unit>
    @POST("accounts/logout")
    suspend fun logout(): Response<Unit>
}