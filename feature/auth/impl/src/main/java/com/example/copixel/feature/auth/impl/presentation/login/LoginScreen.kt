package com.example.copixel.feature.auth.impl.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
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
import com.example.copixel.feature.auth.api.destinations.RegisterDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)
    val eventHandler = viewModel.eventHandler

    LaunchedEffect(action) {
        when (action) {
            LoginSideEffect.NavigateProfile -> navController.navigate(GraphRoutes.PROFILE)
            LoginSideEffect.NavigateRegister -> navController.navigate(RegisterDestination.defaultRoute)
            else -> Unit
        }
    }

    SignInMainContent(state = state, eventHandler = eventHandler)
    Dialogs(state = state, eventHandler = eventHandler)
}

@Composable
fun SignInMainContent(state: LoginState, eventHandler: (LoginEvent) -> Unit) {
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
                text = "войти",
                modifier = Modifier,
                style = CopixelTheme.typography.bold40,
                color = CopixelTheme.colors.primaryBackground
            )
        }

        // fields
        Column {
            AuthTextField("username", state.username) {
                eventHandler.invoke(LoginEvent.OnUsernameChange(it))
            }

            Spacer(modifier = Modifier.height(20.dp))
            AuthPasswordField(
                "password", state.password,
                onChange = { eventHandler.invoke(LoginEvent.OnPasswordChange(it)) },
                state.passwordVisible,
                onVisibleChange = { eventHandler.invoke(LoginEvent.OnPasswordVisibilityChange) }
            )

            ForgotPassButton {
                //TODO
            }
        }

        // Buttons
        Column {
            AuthButton(
                onClick = { eventHandler.invoke(LoginEvent.OnLoginButtonClick) },
                text = "register",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        AuthBottomText("forgot_pass??", "register") {
            eventHandler.invoke(LoginEvent.OnRegisterButtonCLick)
        }
    }
}

@Composable
private fun Dialogs(state: LoginState, eventHandler: (LoginEvent) -> Unit) {

    if (state.showLoadingProgressBar)
        LoadingDialog(
            "Login...",
            onDismiss = { eventHandler.invoke(LoginEvent.OnDismissLoginRequest) }
        )

    if (state.showErrorDialog)
        ErrorDialog(
            title = "login fail",
            errors = state.errors,
            onDismiss = { eventHandler.invoke(LoginEvent.OnDismissErrorDialog) }
        )
}

@Composable
fun ForgotPassButton(onclick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextButton(
            onClick = onclick,
            modifier = Modifier
                .align(Alignment.CenterEnd),
        ) {
            Text(
                text = "forgot pass?",
                color = CopixelTheme.colors.primaryBackground,
                style = CopixelTheme.typography.medium16,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}