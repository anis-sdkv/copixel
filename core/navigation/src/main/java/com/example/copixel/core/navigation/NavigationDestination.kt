package com.example.copixel.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController

interface NavigationDestination {
    val defaultRoute: String
    val arguments: List<NamedNavArgument>
        get() = emptyList()
}
