package com.example.copixel.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class AppColors(
    val accent: Color,
    val onAccent: Color,
    val tint: Color,
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val surface: Color,
    val solid: Color,
    val outline: Color,
    val error: Color,
    val starColor: Color
)

data class AppTypography(
    val bold40: TextStyle,
    val bold24: TextStyle,
    val bold20: TextStyle,
    val bold16: TextStyle,
    val semibold20: TextStyle,
    val semibold16: TextStyle,
    val semibold14: TextStyle,
    val medium16: TextStyle,
    val medium13: TextStyle,
    val medium12: TextStyle,
    val regular16: TextStyle,
    val extraBold26: TextStyle
)

object CopixelTheme {
    val colors: AppColors
        @Composable
        get() = LocalWanderlustColors.current

    val typography: AppTypography
        @Composable
        get() = LocalWanderlustTypography.current
}

val LocalWanderlustColors = staticCompositionLocalOf<AppColors> {
    error("No colors provided")
}

val LocalWanderlustTypography = staticCompositionLocalOf<AppTypography> {
    error("No font provided")
}
