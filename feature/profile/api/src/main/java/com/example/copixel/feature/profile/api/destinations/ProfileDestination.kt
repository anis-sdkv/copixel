package com.example.copixel.feature.profile.api.destinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.copixel.core.navigation.NavigationDestination


object ProfileDestination : NavigationDestination {
    private const val USERNAME_ARG = "username"
    private const val BASE_ROUTE = "profile/{$USERNAME_ARG}"
    override val defaultRoute: String = BASE_ROUTE
    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(USERNAME_ARG) { type = NavType.StringType }
        )
    fun createRoute(username: String) = "BASE_ROUTE/$username"
}