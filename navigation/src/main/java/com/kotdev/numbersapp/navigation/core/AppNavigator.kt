package com.kotdev.numbersapp.navigation.core

import com.kotdev.numbersapp.navigation.core.destination.Destination
import com.kotdev.numbersapp.navigation.destination.MainDestination
import com.kotdev.numbersapp.navigation.destination.MainNumbersSaved
import kotlinx.coroutines.flow.last
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject

class AppNavigator @Inject constructor() : Navigator() {

    suspend fun toDestination() = _operations.last().destination.getDestination()

    fun navigateWithClearPreviousScreen(to: Destination) {
        _operations.tryEmit(
            Operation(
                destination = to,
                navOptions = {
                    popUpTo(to) {
                        inclusive = true
                    }
                    launchSingleTop = true
                },
            )
        )
    }

    fun navigateClearBackStack(to: Destination) {
        _operations.tryEmit(
            Operation(
                destination = to,
                navOptions = {
                    launchSingleTop = true
                    popUpTo(0) {
                        inclusive = true
                    }
                },
            )
        )
    }

    fun push(to: Destination) {
        _operations.tryEmit(
            Operation(
                destination = to,
                navOptions = {
                    popUpTo(to) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                },
            )
        )
    }

    fun <T> backWithArguments(
        to: Destination,
        key: String,
        result: T
    ) {
        with(navControllerFlow.value!!) {
            val backStackEntry = previousBackStackEntry
            backStackEntry?.savedStateHandle?.set(
                key,
                result
            )
            popBackStack(to, false)
        }
    }
}