package com.example.copixel.feature.auth.api.actionresult

sealed interface LoginResult {
    data object Success : LoginResult
    data class Fail(val errorMessage: String? = null) : LoginResult
}