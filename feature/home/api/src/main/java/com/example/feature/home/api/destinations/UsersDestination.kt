package com.example.feature.home.api.destinations

import com.example.copixel.core.navigation.NavigationDestination

object UsersDestination : NavigationDestination {
    private const val BASE_ROUTE = "users"
    override val defaultRoute: String = BASE_ROUTE

    fun createRoute() = BASE_ROUTE
}