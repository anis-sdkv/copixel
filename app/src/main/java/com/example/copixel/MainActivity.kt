package com.example.copixel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.copixel.core.designsystem.theme.CopixelTheme
import com.example.copixel.navigation.NavHostWithBottomBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CopixelTheme(darkMode = false) {
                NavHostWithBottomBar(navController = navController)
            }
        }
    }
}