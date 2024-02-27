package com.example.copixel.core.designsystem.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.copixel.core.designsystem.theme.CopixelTheme

@Composable
fun AuthBottomText(descriptionText: String, btnText: String, onClick: () -> Unit) {
    Row(
        Modifier.padding(bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            descriptionText,
            style = CopixelTheme.typography.medium16,
            color = CopixelTheme.colors.primaryBackground
        )
        TextButton(onClick = onClick) {
            Text(
                btnText,
                style = CopixelTheme.typography.medium16,
                color = CopixelTheme.colors.primaryBackground,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}