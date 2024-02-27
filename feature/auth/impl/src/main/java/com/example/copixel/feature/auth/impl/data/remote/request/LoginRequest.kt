package com.example.copixel.feature.auth.impl.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("user_name")
    val username: String,
    val password: String
)