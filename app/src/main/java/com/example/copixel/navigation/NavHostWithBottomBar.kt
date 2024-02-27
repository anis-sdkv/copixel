package com.example.copixel.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.copixel.navigation.bottombar.BottomBar
import com.example.copixel.navigation.graph.RootNavGraph

@Composable
fun NavHostWithBottomBar(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        RootNavGraph(navController, Modifier.padding(it))
    }
}