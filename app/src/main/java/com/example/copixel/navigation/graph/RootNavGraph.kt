package com.example.copixel.navigation.graph

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.copixel.core.navigation.GraphRoutes
import com.example.copixel.feature.auth.api.destinations.LoginDestination
import com.example.copixel.feature.canvas.api.destinations.CanvasParamsDestination
import com.example.copixel.feature.profile.api.destinations.ProfileDestination
import com.example.feature.home.api.destinations.UsersDestination

@Composable
fun RootNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    //TODO remove
    val stackState = navController.currentBackStack.value.joinToString("->") { backStackEntry ->
        "{" + backStackEntry.destination.hierarchy.toList().map { it.route }.joinToString("<-") + " }"
    }


    NavHost(navController = navController, startDestination = GraphRoutes.HOME,
        enterTransition = {
            EnterTransition.None
        }, exitTransition = {
            ExitTransition.None
        },
        modifier = modifier
    ) {
        //TODO remove
        Log.i("test", "stack:\n$stackState")

        navigation(startDestination = UsersDestination.defaultRoute, route = GraphRoutes.HOME) {
            homeDestinations.forEach { destination ->
                composable(destination.first.defaultRoute, destination.first.arguments) {
                    destination.second(navController)
                }
            }
        }


        navigation(startDestination = CanvasParamsDestination.defaultRoute, route = GraphRoutes.CANVAS) {
            canvasDestinations.forEach { destination ->
                composable(destination.first.defaultRoute, destination.first.arguments) {
                    destination.second(navController)
                }
            }
        }

        navigation(startDestination = ProfileDestination.defaultRoute, route = GraphRoutes.PROFILE) {
            profileDestinations.forEach { destination ->
                composable(destination.first.defaultRoute, destination.first.arguments) {
                    destination.second(navController)
                }
            }
        }

        navigation(startDestination = LoginDestination.defaultRoute, route = GraphRoutes.AUTH) {
            authDestinations.forEach { destination ->
                composable(destination.first.defaultRoute, destination.first.arguments) {
                    destination.second(navController)
                }
            }
        }
    }
}
