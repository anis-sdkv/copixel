package com.example.feature.canvas.impl.presentation.canvasparams

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.copixel.core.navigation.GraphRoutes
import com.example.copixel.feature.canvas.api.destinations.CanvasDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun CanvasParamsScreen(navController: NavController, viewModel: CanvasParamsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)
    val eventHandler = viewModel.eventHandler

    LaunchedEffect(action) {
        when (action) {
            CanvasParamsSideEffect.NavigateCanvas -> navController.navigate(CanvasDestination.defaultRoute)
            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text("canvasParams")

            Text("width")
            TextField(
                value = state.width.toString(),
                onValueChange = { width -> eventHandler.invoke(CanvasParamsEvent.OnWidthChange(width.toInt())) }
            )

            Text("height")
            TextField(
                value = state.height.toString(),
                onValueChange = { height -> eventHandler.invoke(CanvasParamsEvent.OnWidthChange(height.toInt())) }
            )

            Button(onClick = { eventHandler.invoke(CanvasParamsEvent.OnCreateButtonClick) }) {
                Text(text = "Create")
            }
        }
    }
}