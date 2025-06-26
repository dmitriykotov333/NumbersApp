package com.kotdev.numbersapp.presentation.viewmodels.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotdev.numbersapp.core.network.NetworkObserver
import com.kotdev.numbersapp.core_ui.components.ConnectivityStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkObserver: NetworkObserver
) : ViewModel() {

    val isConnected = networkObserver.connectionState.stateIn(
        scope = viewModelScope, started = SharingStarted.Companion.WhileSubscribed(5000L),
        ConnectivityStatus.Available
    )

    init {
        networkObserver.register()
    }

    override fun onCleared() {
        super.onCleared()
        networkObserver.unregister()
    }
}