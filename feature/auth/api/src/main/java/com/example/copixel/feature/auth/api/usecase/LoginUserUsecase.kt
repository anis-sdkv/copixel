package com.example.copixel.feature.auth.api.usecase

import com.example.copixel.feature.auth.api.actionresult.LoginResult
import com.example.copixel.feature.auth.api.servise.AuthService

class LoginUserUsecase(private val authService: AuthService) {
    suspend operator fun invoke(username: String, password: String): LoginResult =
        try {
            authService.login(username, password)
        } catch (e: Exception) {
            LoginResult.Fail(e.message)
        }
}