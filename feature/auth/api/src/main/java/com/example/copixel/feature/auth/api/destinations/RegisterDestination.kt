package com.example.copixel.feature.auth.api.destinations

import com.example.copixel.core.navigation.NavigationDestination

object RegisterDestination : NavigationDestination {
    private const val BASE_ROUTE = "register"
    override val defaultRoute: String = BASE_ROUTE
    fun createRoute() = "$BASE_ROUTE"
}