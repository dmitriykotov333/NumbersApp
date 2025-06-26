package com.kotdev.numbersapp.presentation.nav

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kotdev.numbersapp.core.extensions.joinStrings
import com.kotdev.numbersapp.core.extensions.openUrl
import com.kotdev.numbersapp.core.extensions.share
import com.kotdev.numbersapp.core_ui.extensions.setLightStatusBar
import com.kotdev.numbersapp.navigation.core.AppNavigator
import com.kotdev.numbersapp.navigation.core.provider.NavEntryPointProvider
import com.kotdev.numbersapp.navigation.destination.DetailDestination
import com.kotdev.numbersapp.navigation.destination.MainArgsType
import com.kotdev.numbersapp.navigation.destination.MainDestination
import com.kotdev.numbersapp.navigation.destination.MainNavigation
import com.kotdev.numbersapp.navigation.destination.MainNumbersSaved
import com.kotdev.numbersapp.presentation.screens.DetailScreen
import com.kotdev.numbersapp.presentation.screens.MainScreen
import com.kotdev.numbersapp.presentation.viewmodels.detail.DetailAction
import com.kotdev.numbersapp.presentation.viewmodels.detail.DetailViewModel
import com.kotdev.numbersapp.presentation.viewmodels.main.MainAction
import com.kotdev.numbersapp.presentation.viewmodels.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.Json
import se.ansman.dagger.auto.AutoBindIntoSet
import javax.inject.Inject
import kotlin.reflect.typeOf


@AutoBindIntoSet
class MainNavProvider @Inject constructor(
    private val navigator: AppNavigator,
) : NavEntryPointProvider {

    override val routeItem = NavEntryPointProvider.RouteItem(
        route = MainNavigation,
        isStart = true
    )

    override fun graph(
        builder: NavGraphBuilder,
        snackbar: SnackbarHostState,
        paddingValues: PaddingValues
    ) = builder.run {

        navigation<MainNavigation>(
            startDestination = MainDestination
        ) {
            composable<MainDestination> { backStackEntry ->
                val viewModel = hiltViewModel<MainViewModel>()
                MainAction(viewModel, snackbar)
                MainScreen(viewModel, paddingValues)
            }
            composable<DetailDestination>(
                typeMap = mapOf(
                    typeOf<MainNumbersSaved>() to MainArgsType
                ),
                enterTransition = {
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(durationMillis = 1000)
                    ) + fadeIn(animationSpec = tween(1000))
                },
                popEnterTransition = {
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(durationMillis = 1000)
                    ) + fadeIn(animationSpec = tween(1000))
                },
                exitTransition = {
                    slideOutVertically(
                        targetOffsetY = { fullHeight -> -fullHeight },
                        animationSpec = tween(durationMillis = 1000)
                    ) + fadeOut(animationSpec = tween(1000))
                },
                popExitTransition = {
                    slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(durationMillis = 1000)
                    ) + fadeOut(animationSpec = tween(1000))
                }
            ) { backStackEntry ->
                val viewModel = hiltViewModel<DetailViewModel>()
                DetailAction(viewModel, snackbar)
                DetailScreen(viewModel)
            }
        }
    }

    @Composable
    private fun MainAction(viewModel: MainViewModel, snackbar: SnackbarHostState) {
        val context = LocalContext.current
        val vibrator = remember {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                manager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
        }
        var vibration by rememberSaveable {
            mutableStateOf(false)
        }
        LaunchedEffect(Unit) {
            viewModel.actions().collectLatest {
                when (it) {
                    is MainAction.ErrorShow -> {
                        snackbar.showSnackbar(it.value)
                    }

                    is MainAction.Vibrate -> {
                        vibration = it.value
                    }
                }
            }
        }
        DisposableEffect(vibration) {
            if (vibration) {
                vibrator.vibrate(
                    VibrationEffect.createWaveform(
                        longArrayOf(
                            0,
                            40,
                            200
                        ), 0
                    )
                )
            } else {
                vibrator.cancel()
            }

            onDispose {
                vibrator.cancel()
            }
        }
    }

    @Composable
    private fun DetailAction(viewModel: DetailViewModel, snackbar: SnackbarHostState) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            viewModel.actions().collectLatest {
                when (it) {
                    is DetailAction.OpenUrl -> {
                        context.openUrl(it.value)

                    }

                    is DetailAction.Share -> {
                        context.share(it.value)
                    }
                }
            }
        }
    }
}
