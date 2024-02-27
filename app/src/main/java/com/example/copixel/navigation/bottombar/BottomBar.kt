package com.example.copixel.navigation.bottombar

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.copixel.core.designsystem.theme.CopixelTheme

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        BottomNavigationItem.asList.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(27.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CopixelTheme.colors.accent,
                    unselectedIconColor = CopixelTheme.colors.primaryText.copy(0.4f),
                    indicatorColor = CopixelTheme.colors.primaryBackground,
                ),
                alwaysShowLabel = false,
                selected = currentDestination?.hierarchy?.any { it.route == item.graphRoute } == true,
                onClick = {
                    navController.navigate(item.graphRoute) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                    }

                }
            )
        }
    }
}