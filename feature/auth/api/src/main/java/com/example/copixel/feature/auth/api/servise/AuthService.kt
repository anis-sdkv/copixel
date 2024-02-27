package com.example.copixel.feature.auth.api.servise

import com.example.copixel.feature.auth.api.actionresult.LoginResult
import com.example.copixel.feature.auth.api.actionresult.LogoutResult
import com.example.copixel.feature.auth.api.actionresult.RegisterResult

interface AuthService {
    suspend fun register(username: String, email: String, password: String): RegisterResult
    suspend fun login(username: String, password: String): LoginResult
    suspend fun updatePassword(newPassword: String): Boolean
    suspend fun logout(): LogoutResult
}