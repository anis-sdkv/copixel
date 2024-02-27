package com.example.copixel.feature.auth.api.actionresult

sealed interface RegisterResult {
    data object Success : RegisterResult
    data class Fail(val errorMessage: String? = null) : RegisterResult
}