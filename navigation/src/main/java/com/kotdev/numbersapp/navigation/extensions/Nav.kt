package com.kotdev.numbersapp.navigation.extensions

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.kotdev.numbersapp.navigation.core.destination.Destination

@Composable
fun NavController.isLight(activity: Activity, dest: List<Destination>) {
    val view = LocalView.current
    addOnDestinationChangedListener { _, destination, _ ->
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars =
            dest.any { destination.route != it.getDestination() }
    }
}