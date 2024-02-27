package com.example.copixel.feature.auth.impl.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.copixel.core.designsystem.theme.CopixelTheme
import com.example.copixel.core.designsystem.widget.AuthBottomText
import com.example.copixel.core.designsystem.widget.AuthButton
import com.example.copixel.core.designsystem.widget.AuthPasswordField
import com.example.copixel.core.designsystem.widget.AuthTextField
import com.example.copixel.core.designsystem.widget.authGradient
import com.example.copixel.core.designsystem.widget.dialogs.ErrorDialog
import com.example.copixel.core.designsystem.widget.dialogs.LoadingDialog
import com.example.copixel.core.navigation.GraphRoutes
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)
    val eventHandler = viewModel.eventHandler

    LaunchedEffect(action) {
        when (action) {
            RegisterSideEffect.NavigateProfile -> navController.navigate(GraphRoutes.PROFILE)
            RegisterSideEffect.NavigateLogin -> navController.navigateUp()
            else -> Unit
        }
    }

    RegisterMainContent(state = state, eventHandler = eventHandler)
    Dialogs(state = state, eventHandler = eventHandler)
}

@Composable
private fun RegisterMainContent(state: RegisterState, eventHandler: (RegisterEvent) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .authGradient()
            .padding(horizontal = 20.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = "reg",
                modifier = Modifier,
                style = CopixelTheme.typography.bold40,
                color = CopixelTheme.colors.primaryBackground
            )
        }

        Column {
            AuthTextField(
                "name",
                state.username
            ) { eventHandler.invoke(RegisterEvent.OnUsernameChange(it)) }

            Spacer(modifier = Modifier.height(20.dp))
            AuthTextField(
                "email",
                state.email
            ) { eventHandler.invoke(RegisterEvent.OnEmailChange(it)) }

            Spacer(modifier = Modifier.height(20.dp))
            AuthPasswordField(
                "pass",
                state.password,
                { eventHandler.invoke(RegisterEvent.OnPasswordChange(it)) },
                state.passwordVisible,
                { eventHandler.invoke(RegisterEvent.OnPasswordVisibilityChange) })

            Spacer(modifier = Modifier.height(20.dp))
            AuthPasswordField(
                "repeat",
                state.confirmPassword,
                { eventHandler.invoke(RegisterEvent.OnConfirmPasswordChange(it)) },
                state.passwordVisible,
                { eventHandler.invoke(RegisterEvent.OnPasswordVisibilityChange) })
        }


        AuthButton(
            onClick = { eventHandler.invoke(RegisterEvent.OnRegisterButtonClick) },
            text = "REG!!!!!",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(60.dp)
                .align(Alignment.CenterHorizontally)
        )

        AuthBottomText(
            "have acc?",
            "login"
        ) { eventHandler.invoke(RegisterEvent.OnSignInButtonCLick) }
    }
}

@Composable
private fun Dialogs(state: RegisterState, eventHandler: (RegisterEvent) -> Unit) {
    if (state.showLoadingProgressBar)
        LoadingDialog(
            "loading",
            onDismiss = { eventHandler.invoke(RegisterEvent.OnDismissRegisterRequest) }
        )

    if (state.showErrorDialog)
        ErrorDialog(
            "reg fail",
            errors = state.errors,
            onDismiss = { eventHandler.invoke(RegisterEvent.OnDismissErrorDialog) }
        )
}