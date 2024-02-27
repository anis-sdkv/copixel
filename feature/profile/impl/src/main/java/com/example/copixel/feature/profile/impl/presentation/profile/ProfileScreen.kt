package com.example.copixel.feature.profile.impl.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.copixel.core.navigation.GraphRoutes
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinInternalApi

@OptIn(KoinInternalApi::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)
    val eventHandler = viewModel.eventHandler

    LaunchedEffect(action) {
        when (action) {
            ProfileSideEffect.NavigateAuth -> navController.navigate(GraphRoutes.AUTH)
            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text("Profile")
            Button(onClick = { eventHandler.invoke(ProfileEvent.OnLoginButtonClick) }) {
                Text(text = "Goto login")
            }
        }
    }
}