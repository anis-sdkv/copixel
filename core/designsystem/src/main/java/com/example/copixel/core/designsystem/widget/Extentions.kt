package com.example.copixel.core.designsystem.widget

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.copixel.core.designsystem.theme.CopixelTheme

fun Modifier.authGradient(): Modifier = composed {
    val configuration = LocalConfiguration.current
    val screenSize =
        Size(LocalDensity.current.run {
            configuration.screenWidthDp.dp.toPx()
        }, LocalDensity.current.run {
            configuration.screenHeightDp.dp.toPx()
        })

    this.background(
        ShaderBrush(
            RadialGradientShader(
                colors = listOf(CopixelTheme.colors.tint, CopixelTheme.colors.accent),
                center = Offset(screenSize.width / 2f, screenSize.height / 3f),
                radius = screenSize.maxDimension / 2f,
                colorStops = listOf(0f, 0.95f)
            )
        )
    )
}