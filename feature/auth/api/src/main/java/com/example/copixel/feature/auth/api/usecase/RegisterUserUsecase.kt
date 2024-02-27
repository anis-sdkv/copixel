package com.example.copixel.feature.auth.api.usecase

import com.example.copixel.feature.auth.api.actionresult.RegisterResult
import com.example.copixel.feature.auth.api.servise.AuthService

class RegisterUserUsecase(private val authService: AuthService) {
    suspend operator fun invoke(username: String, email: String, password: String): RegisterResult =
        try {
            authService.register(username, email, password)
        } catch (e: Exception) {
            RegisterResult.Fail(e.message)
        }
}