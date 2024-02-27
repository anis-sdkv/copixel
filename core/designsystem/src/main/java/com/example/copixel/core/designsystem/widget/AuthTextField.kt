package com.example.copixel.core.designsystem.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.copixel.core.designsystem.theme.CopixelTheme

@Composable
fun AuthTextField(label: String, value: String, onChange: (String) -> Unit) {
    Column {
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
            text = label,
            style = CopixelTheme.typography.semibold16,
            color = CopixelTheme.colors.primaryBackground
        )
        TextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            singleLine = true,
            textStyle = CopixelTheme.typography.semibold16,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = CopixelTheme.colors.primaryBackground,
                unfocusedContainerColor = CopixelTheme.colors.primaryBackground,
                disabledContainerColor = CopixelTheme.colors.primaryBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}
