package com.example.copixel.feature.auth.impl.data.servise

import android.util.Log
import com.example.copixel.feature.auth.api.actionresult.LoginResult
import com.example.copixel.feature.auth.api.actionresult.LogoutResult
import com.example.copixel.feature.auth.api.actionresult.RegisterResult
import com.example.copixel.feature.auth.api.servise.AuthService
import com.example.copixel.feature.auth.impl.data.remote.CopixelAuthApi
import com.example.copixel.feature.auth.impl.data.remote.request.LoginRequest
import com.example.copixel.feature.auth.impl.data.remote.request.RegisterRequest
import kotlin.coroutines.coroutineContext

class AuthServiceImpl(val authApi: CopixelAuthApi) : AuthService {
    override suspend fun register(username: String, email: String, password: String): RegisterResult {
        Log.i("test", coroutineContext.toString())
        val response = authApi.register(RegisterRequest(username, email, password))
        if (response.isSuccessful) return RegisterResult.Success
        return RegisterResult.Fail(response.message())
    }

    override suspend fun login(username: String, password: String): LoginResult {
        val response = authApi.login(LoginRequest(username, password))
        if (response.isSuccessful) return LoginResult.Success
        return LoginResult.Fail(response.message())
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        TODO()
    }

    override suspend fun logout(): LogoutResult {
        val response = authApi.logout()
        if (response.isSuccessful) return LogoutResult.Success
        return LogoutResult.Fail(response.message())
    }
}