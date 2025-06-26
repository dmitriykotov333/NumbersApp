package com.kotdev.numbersapp.navigation.core.provider

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import com.kotdev.numbersapp.navigation.core.destination.Destination

interface NavEntryPointProvider {

    data class RouteItem(
        val route: Destination,
        val isStart: Boolean,
    )

    val routeItem: RouteItem

    fun graph(
        builder: NavGraphBuilder,
        snackbar: SnackbarHostState,
        paddingValues: PaddingValues
    )

}