package com.example.copixel.core.designsystem.widget.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.copixel.core.designsystem.theme.CopixelTheme

@Composable
fun ErrorDialog(title: String, errors: List<String>, onDismiss: () -> Unit) {
    AlertDialog(
        containerColor = CopixelTheme.colors.primaryBackground,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = title,
                style = CopixelTheme.typography.semibold16,
                color = CopixelTheme.colors.primaryText
            )
        },
        text = {
            LazyColumn {
                items(errors.size) {
                    Text(
                        text = errors[it],
                        modifier = Modifier.padding(vertical = 4.dp),
                        style = CopixelTheme.typography.medium12,
                        color = CopixelTheme.colors.error
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "OK",
                    style = CopixelTheme.typography.semibold14,
                    color = CopixelTheme.colors.accent
                )
            }
        },
        onDismissRequest = onDismiss,
    )
}