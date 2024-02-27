package com.example.copixel.feature.auth.impl.presentation.register

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.copixel.core.base.BaseViewModel
import com.example.copixel.feature.auth.api.actionresult.RegisterResult
import com.example.copixel.feature.auth.api.usecase.RegisterUserUsecase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Immutable
data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val showLoadingProgressBar: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errors: PersistentList<String> = persistentListOf()
)

sealed interface RegisterSideEffect {
    data object NavigateProfile : RegisterSideEffect
    data object NavigateLogin : RegisterSideEffect
}

sealed interface RegisterEvent {
    data object OnRegisterButtonClick : RegisterEvent
    data object OnSignInButtonCLick : RegisterEvent
    data object OnPasswordVisibilityChange : RegisterEvent
    data object OnDismissRegisterRequest : RegisterEvent
    data object OnDismissErrorDialog : RegisterEvent
    data class OnUsernameChange(val value: String) : RegisterEvent
    data class OnEmailChange(val value: String) : RegisterEvent
    data class OnPasswordChange(val value: String) : RegisterEvent
    data class OnConfirmPasswordChange(val value: String) : RegisterEvent
}

class RegisterViewModel constructor(
    private val register: RegisterUserUsecase
) : BaseViewModel<RegisterState, RegisterSideEffect, RegisterEvent>() {
    override val internalState: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    override fun event(event: RegisterEvent) {
        when (event) {
            RegisterEvent.OnRegisterButtonClick -> onRegisterButtonClick()
            RegisterEvent.OnSignInButtonCLick -> onSignInButtonClick()
            RegisterEvent.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
            RegisterEvent.OnDismissErrorDialog -> onDismissErrorDialog()
            RegisterEvent.OnDismissRegisterRequest -> onDismissRegisterRequest()
            is RegisterEvent.OnUsernameChange -> onUsernameChange(event.value)
            is RegisterEvent.OnEmailChange -> onEmailChange(event.value)
            is RegisterEvent.OnPasswordChange -> onPasswordChange(event.value)
            is RegisterEvent.OnConfirmPasswordChange -> onConfirmPasswordChange(event.value)
        }
    }

    private fun onUsernameChange(username: String) {
        updateState(state.value.copy(username = username))
    }

    private fun onEmailChange(email: String) {
        updateState(state.value.copy(email = email))
    }

    private fun onPasswordChange(password: String) {
        updateState(state.value.copy(password = password))
    }

    private fun onConfirmPasswordChange(password: String) {
        updateState(state.value.copy(confirmPassword = password))
    }

    private fun onPasswordVisibilityChange() {
        updateState(state.value.copy(passwordVisible = !state.value.passwordVisible))
    }

    private fun onDismissErrorDialog() {
        updateState(state.value.copy(showErrorDialog = false))
    }

    private fun onDismissRegisterRequest() {
        currentJob?.cancel()
        updateState(state.value.copy(showLoadingProgressBar = false))
    }

    private fun onSignInButtonClick() {
        currentJob?.cancel()
        emitAction(RegisterSideEffect.NavigateLogin)
    }

    private fun onRegisterButtonClick() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val errors = mutableListOf<String>()
            internalState.emit(state.value.copy(showLoadingProgressBar = true))
            val result = if (validateFields(errors)) with(state.value) { register(username, email, password) }
            else RegisterResult.Fail()
            internalState.emit(state.value.copy(showLoadingProgressBar = false))

            when (result) {
                is RegisterResult.Success -> {
                    errors.add("Success")
                    internalState.emit(state.value.copy(showErrorDialog = true, errors = errors.toPersistentList()))
                }

                is RegisterResult.Fail -> {
                    result.errorMessage?.let { errors.add(it) }
                    internalState.emit(state.value.copy(showErrorDialog = true, errors = errors.toPersistentList()))
                }
            }
        }
    }

    private fun validateFields(errors: MutableList<String>): Boolean {
        if (state.value.username.length < 3) {
            errors.add("Имя пользователя должно состоять не менее чем из 3 символов.")
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()) {
            errors.add("Неверный формат почты.")
            return false
        }

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

        if (password != state.value.confirmPassword) {
            passValidate = false
            errors.add("Пароли не совпадают.")
        }

        return passValidate
    }
}