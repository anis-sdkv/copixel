package com.example.copixel.core.designsystem.widget.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.copixel.core.designsystem.theme.CopixelTheme

@Composable
fun LoadingDialog(text: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .background(CopixelTheme.colors.primaryBackground, shape = RoundedCornerShape(20.dp))
        ) {
            CircularProgressIndicator(color = CopixelTheme.colors.accent)

            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                text = text,
                style = CopixelTheme.typography.semibold16,
                color = CopixelTheme.colors.primaryText
            )
        }
    }
}