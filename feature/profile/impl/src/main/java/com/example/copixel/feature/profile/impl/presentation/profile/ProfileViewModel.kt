package com.example.copixel.feature.profile.impl.presentation.profile

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.copixel.core.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Immutable
data class ProfileState(
    val todo: String = ""
)

sealed interface ProfileSideEffect {
    data object NavigateAuth : ProfileSideEffect
}

sealed interface ProfileEvent {
    data object OnLoginButtonClick : ProfileEvent
}

class ProfileViewModel : BaseViewModel<ProfileState, ProfileSideEffect, ProfileEvent>() {
    override val internalState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    override fun event(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnLoginButtonClick -> onLoginButtonClick()
        }
    }

    private fun onLoginButtonClick() {
        viewModelScope.launch { internalAction.emit(ProfileSideEffect.NavigateAuth) }
    }
}