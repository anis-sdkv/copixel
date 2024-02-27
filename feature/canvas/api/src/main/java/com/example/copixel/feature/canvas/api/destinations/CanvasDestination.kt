package com.example.copixel.feature.canvas.api.destinations

import com.example.copixel.core.navigation.NavigationDestination

object CanvasDestination : NavigationDestination {
    private const val BASE_ROUTE = "canvas"
    override val defaultRoute: String = BASE_ROUTE
    fun createRoute() = BASE_ROUTE
}