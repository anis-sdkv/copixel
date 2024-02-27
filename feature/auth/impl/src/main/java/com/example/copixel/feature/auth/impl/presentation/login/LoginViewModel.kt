package com.example.copixel.feature.auth.impl.presentation.login

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.copixel.core.base.BaseViewModel
import com.example.copixel.feature.auth.api.actionresult.LoginResult
import com.example.copixel.feature.auth.api.usecase.LoginUserUsecase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@Immutable
data class LoginState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val showLoadingProgressBar: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errors: PersistentList<String> = persistentListOf()
)

sealed interface LoginSideEffect {
    data object NavigateRegister : LoginSideEffect
    data object NavigateProfile : LoginSideEffect
}

sealed interface LoginEvent {
    data object OnLoginButtonClick : LoginEvent
    data object OnRegisterButtonCLick : LoginEvent
    data object OnPasswordVisibilityChange : LoginEvent
    data object OnDismissLoginRequest : LoginEvent
    data object OnDismissErrorDialog : LoginEvent
    data class OnUsernameChange(val value: String) : LoginEvent
    data class OnPasswordChange(val value: String) : LoginEvent
}

class LoginViewModel(private val login: LoginUserUsecase) : BaseViewModel<LoginState, LoginSideEffect, LoginEvent>() {
    override val internalState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    override fun event(event: LoginEvent) {
        when (event) {
            LoginEvent.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
            is LoginEvent.OnUsernameChange -> onUsernameChange(event.value)
            is LoginEvent.OnPasswordChange -> onPasswordChange(event.value)
            LoginEvent.OnLoginButtonClick -> onLoginButtonClick()
            LoginEvent.OnRegisterButtonCLick -> onRegisterButtonCLick()
            LoginEvent.OnDismissErrorDialog -> onDismissErrorDialog()
            LoginEvent.OnDismissLoginRequest -> onDismissLoginRequest()
        }
    }

    private fun onUsernameChange(username: String) {
        updateState(internalState.value.copy(username = username))
    }

    private fun onPasswordChange(password: String) {
        updateState(internalState.value.copy(password = password))
    }

    private fun onPasswordVisibilityChange() {
        updateState(internalState.value.copy(passwordVisible = !internalState.value.passwordVisible))
    }

    private fun onRegisterButtonCLick() {
        emitAction(LoginSideEffect.NavigateRegister)
    }

    private fun onDismissErrorDialog() {
        updateState(state.value.copy(showErrorDialog = false))
    }

    private fun onDismissLoginRequest() {
        currentJob?.cancel()
        updateState(state.value.copy(showLoadingProgressBar = false))
    }

    private fun onLoginButtonClick() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val errors = mutableListOf<String>()

            internalState.emit(state.value.copy(showLoadingProgressBar = true))
            val result = if (validateFields(errors)) with(state.value) { login(username, password) }
            else LoginResult.Fail()
            internalState.emit(state.value.copy(showLoadingProgressBar = false))

            when (result) {
                LoginResult.Success -> {
                    internalAction.emit(LoginSideEffect.NavigateProfile)
                }

                is LoginResult.Fail -> {
                    result.errorMessage?.let { errors.add(it) }
                    internalState.emit(state.value.copy(showErrorDialog = true, errors = errors.toPersistentList()))
                }
            }
        }
    }

    private fun validateFields(errors: MutableList<String>): Boolean {
        var passValidate = true
        val password = state.value.password
        if (!password.matches(Regex(".*[a-z].*"))) {
            errors.add("Пароль должен содержать хотя бы одну маленькую букву.")
            passValidate = false
        }

        if (!password.matches(Regex(".*[A-Z].*"))) {
            errors.add("Пароль должен содержать хотя бы одну большую букву.")
            passValidate = false
        }

        if (!password.matches(Regex(".*\\d.*"))) {
            passValidate = false
            errors.add("Пароль должен содержать хотя бы одну цифру.")
        }

        if (password.length < 8) {
            passValidate = false
            errors.add("Пароль должен содержать минимум 8 символов.")
        }

        return passValidate
    }
}
