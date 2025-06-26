package com.kotdev.numbersapp.navigation.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.kotdev.numbersapp.navigation.destination.MainNumbersSaved
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription

abstract class Navigator {

    val navControllerFlow = MutableStateFlow<NavController?>(null)

    protected val _operations = MutableSharedFlow<Operation>(extraBufferCapacity = 3)
    val operations = _operations.asSharedFlow()

    private val _backPressEvents = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val backPressEvents: Flow<Unit> = _backPressEvents.asSharedFlow()

    fun onBackClick() {
        _backPressEvents.tryEmit(Unit)
    }
}

@Composable
fun NavController(
    navigator: AppNavigator,
    navController: NavController,
) {

    LaunchedEffect(Unit) {
        navigator.operations
            .onSubscription {
                navigator.navControllerFlow.value = navController
            }
            .onCompletion {
                navigator.navControllerFlow.value = null
            }
            .onEach { (destination, navOptions) ->
                navController.navigate(destination, navOptions)
            }
            .launchIn(this)
    }

    LaunchedEffect(Unit) {
        navigator.backPressEvents
            .onEach { type ->
                navController.popBackStack()
            }
            .launchIn(this)

    }
}
