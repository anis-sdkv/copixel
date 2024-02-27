package com.example.copixel.feature.auth.api.destinations

import com.example.copixel.core.navigation.NavigationDestination

object LoginDestination : NavigationDestination {
    private const val BASE_ROUTE = "login"
    override val defaultRoute: String = BASE_ROUTE
    fun createRoute() = BASE_ROUTE
}