package com.example.copixel.core.designsystem.theme

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun CopixelTheme(
    darkMode: Boolean?,
    content: @Composable () -> Unit
) {
    val darkTheme = darkMode ?: isSystemInDarkTheme()
    val colors = if (darkTheme) baseDarkPalette else baseLightPalette
    val typography = baseTypography

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );

        }
    }

    CompositionLocalProvider(
        LocalWanderlustColors provides colors,
        LocalWanderlustTypography provides typography,
        content = content
    )
}