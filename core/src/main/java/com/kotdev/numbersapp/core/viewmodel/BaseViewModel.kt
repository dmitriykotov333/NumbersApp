package com.kotdev.numbersapp.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class BaseViewModel<State : Any, Action, Event> constructor(private val initialState: State) :
    ViewModel() {

    protected val coroutineScope: CoroutineScope = viewModelScope

    protected val viewStates = MutableStateFlow(initialState)

    protected val viewActions: Channel<Action> = Channel(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        capacity = 1
    )

    var viewState: State
        get() = viewStates.value
        set(value) {
            viewStates.value = value
        }

    fun states(): StateFlow<State> = viewStates.stateIn(
        coroutineScope, SharingStarted.WhileSubscribed(5000), initialState
    )


    fun actions(): Flow<Action> = viewActions.receiveAsFlow()

    protected fun sendIntent(intent: Action) {
        coroutineScope.launch {
            viewActions.send(intent)
        }
    }

    protected fun updateState(state: State.() -> State) {
        viewStates.update {
            it.state()
        }
    }

    abstract fun obtainEvent(viewEvent: Event)

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

}