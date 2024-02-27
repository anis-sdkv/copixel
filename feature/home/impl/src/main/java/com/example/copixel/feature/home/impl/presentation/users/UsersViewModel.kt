package com.example.copixel.feature.home.impl.presentation.users

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

@Immutable
data class UsersState(
    val todo: String =""
)

sealed interface UsersSideEffect {
    object TODO : UsersSideEffect
}

sealed interface UsersEvent {
    object TODO: UsersEvent
}
class UsersViewModel : ViewModel(){
    private val _state: MutableStateFlow<UsersState> = MutableStateFlow(UsersState())
    val state: StateFlow<UsersState> = _state

    private val _action = MutableSharedFlow<UsersSideEffect?>()
    val action: SharedFlow<UsersSideEffect?>
        get() = _action.asSharedFlow()

    private var currentJob: Job? = null
    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
        currentJob = null
    }
}