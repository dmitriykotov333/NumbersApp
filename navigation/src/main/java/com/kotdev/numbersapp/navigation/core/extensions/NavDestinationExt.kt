package com.kotdev.numbersapp.navigation.core.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptionsBuilder
import com.kotdev.numbersapp.navigation.core.destination.Destination

fun NavDestination.belongsTo(route: Destination) =
    hierarchy
        .any { it.route.withoutArgs == route.getDestination().withoutArgs }

private val String?.withoutArgs
    get() = this?.split('?')?.firstOrNull()

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
    }
    launchSingleTop = true
}
