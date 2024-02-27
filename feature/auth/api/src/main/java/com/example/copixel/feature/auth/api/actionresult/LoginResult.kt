package com.example.copixel.feature.auth.api.actionresult

sealed interface LogoutResult {
    data object Success: LogoutResult
    data class Fail(val errorMessage: String? = null) : LogoutResult
}