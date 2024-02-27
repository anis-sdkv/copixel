package com.example.copixel.core.designsystem.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.copixel.core.designsystem.R
import com.example.copixel.core.designsystem.theme.CopixelTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthPasswordField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibleChange: () -> Unit
) {
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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = CopixelTheme.typography.semibold16,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = CopixelTheme.colors.primaryBackground
            ),
            trailingIcon = {
                val image = if (passwordVisible) painterResource(id = R.drawable.ic_show) else painterResource(id = R.drawable.ic_hide)
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = onVisibleChange, modifier = Modifier.padding(end = 8.dp)) {
                    Icon(
                        painter = image,
                        description,
                        tint = CopixelTheme.colors.accent,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        )
    }
}