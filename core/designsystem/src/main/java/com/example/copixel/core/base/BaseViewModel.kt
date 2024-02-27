package com.example.copixel.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<TState, TSideEffect, TEvent> : ViewModel() {
    protected abstract val internalState: MutableStateFlow<TState>
    val state: StateFlow<TState> by lazy { internalState.asStateFlow() }

    protected val internalAction = MutableSharedFlow<TSideEffect?>()
    val action: SharedFlow<TSideEffect?> by lazy { internalAction.asSharedFlow() }

    protected var currentJob: Job? = null
    val eventHandler
        get() = ::event

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
        currentJob = null
    }

    protected abstract fun event(event: TEvent)
    protected fun emitAction(effect: TSideEffect) {
        viewModelScope.launch { internalAction.emit(effect) }
    }

    protected fun updateState(newState: TState) {
        viewModelScope.launch { internalState.emit(newState) }
    }
}