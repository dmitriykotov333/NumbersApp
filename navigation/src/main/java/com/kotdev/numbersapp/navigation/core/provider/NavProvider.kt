package com.kotdev.numbersapp.navigation.core.provider

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import com.kotdev.numbersapp.navigation.core.destination.Destination

interface NavProvider {

    data class BottomBarItem(
        val index: Int,
        @StringRes val labelResId: Int,
        val icon: Int,
        val route: Destination,
        val isStart: Boolean,
    )

    val navBarItem: BottomBarItem

    fun graph(
        builder: NavGraphBuilder,
        snackbar: SnackbarHostState,
        paddingValues: PaddingValues,
    )

}