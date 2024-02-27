package com.example.copixel.core.designsystem.widget

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.copixel.core.designsystem.theme.CopixelTheme

@Composable
fun AuthButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CopixelTheme.colors.primaryBackground,
            contentColor = CopixelTheme.colors.primaryText
        )
    ) {
        Text(
            text = text,
            style = CopixelTheme.typography.semibold14,
            color = CopixelTheme.colors.primaryText
        )
    }
}