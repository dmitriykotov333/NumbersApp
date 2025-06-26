package com.kotdev.numbersapp.core.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.annotation.RequiresPermission
import com.kotdev.numbersapp.core_ui.components.ConnectivityStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkObserver @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _connectionState: MutableStateFlow<ConnectivityStatus> =
        MutableStateFlow(ConnectivityStatus.Available)

    val connectionState = _connectionState.asStateFlow()

    private var state = ConnectivityStatus.Available.name

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _connectionState.value = if (state != ConnectivityStatus.Available.name) {
                ConnectivityStatus.Retry
            } else {
                ConnectivityStatus.Available
            }
            state = ConnectivityStatus.Available.name
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            state = ConnectivityStatus.Losing.name
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            state = ConnectivityStatus.Lost.name
            if (!hasInternetConnection()) {
                _connectionState.value = ConnectivityStatus.Unavailable
            } else {
                _connectionState.value = ConnectivityStatus.Available
            }
        }

        override fun onUnavailable() {
            super.onUnavailable()
            state = ConnectivityStatus.Unavailable.name
            _connectionState.value = (ConnectivityStatus.Unavailable)
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun register() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)
        _connectionState.value = if (checkInitialConnection()) {
            state = ConnectivityStatus.Available.name
            ConnectivityStatus.Available
        } else {
            state = ConnectivityStatus.Unavailable.name
            ConnectivityStatus.Unavailable
        }
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun checkInitialConnection(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}