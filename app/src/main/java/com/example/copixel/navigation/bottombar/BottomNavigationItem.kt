package com.example.copixel.navigation.bottombar

import com.example.copixel.R
import com.example.copixel.core.navigation.GraphRoutes

sealed class BottomNavigationItem(var graphRoute: String, var icon: Int, var title: String) {
    data object Home : BottomNavigationItem(
        GraphRoutes.HOME,
        R.drawable.ic_android_black_24dp,
        "Home"
    )

    data object Canvas : BottomNavigationItem(
        GraphRoutes.CANVAS,
        R.drawable.ic_android_black_24dp,
        "Draw"
    )

    data object Profile : BottomNavigationItem(
        GraphRoutes.PROFILE,
        R.drawable.ic_android_black_24dp,
        "Profile"
    )

    companion object {
        val asList = listOf(Home, Canvas, Profile)
    }
}
