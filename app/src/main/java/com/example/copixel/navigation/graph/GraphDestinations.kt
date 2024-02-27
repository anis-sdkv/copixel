package com.example.copixel.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.copixel.core.navigation.NavigationDestination
import com.example.copixel.feature.auth.api.destinations.LoginDestination
import com.example.copixel.feature.auth.api.destinations.RegisterDestination
import com.example.copixel.feature.auth.impl.presentation.login.LoginScreen
import com.example.copixel.feature.auth.impl.presentation.register.RegisterScreen
import com.example.copixel.feature.canvas.api.destinations.CanvasDestination
import com.example.copixel.feature.canvas.api.destinations.CanvasParamsDestination
import com.example.copixel.feature.home.impl.presentation.users.UsersScreen
import com.example.copixel.feature.profile.api.destinations.ProfileDestination
import com.example.copixel.feature.profile.impl.presentation.profile.ProfileScreen
import com.example.feature.canvas.impl.presentation.canvas.CanvasScreen
import com.example.feature.canvas.impl.presentation.canvasparams.CanvasParamsScreen
import com.example.feature.home.api.destinations.UsersDestination


val authDestinations: List<Pair<NavigationDestination, @Composable (NavController) -> Unit>> =
    listOf(
        RegisterDestination to { navController -> RegisterScreen(navController) },
        LoginDestination to { navController -> LoginScreen(navController) }
    )

val homeDestinations: List<Pair<NavigationDestination, @Composable (NavController) -> Unit>> =
    listOf(
        UsersDestination to { navController -> UsersScreen(navController) },
    )

val canvasDestinations: List<Pair<NavigationDestination, @Composable (NavController) -> Unit>> =
    listOf(
        CanvasParamsDestination to { navController -> CanvasParamsScreen(navController) },
        CanvasDestination to { navController -> CanvasScreen(navController) },
    )


val profileDestinations: List<Pair<NavigationDestination, @Composable (NavController) -> Unit>> =
    listOf(
        ProfileDestination to { navController -> ProfileScreen(navController) },
    )