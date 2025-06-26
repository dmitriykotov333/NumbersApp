package com.kotdev.numbersapp.navigation.core

import androidx.navigation.NavOptionsBuilder
import com.kotdev.numbersapp.navigation.core.destination.Destination

data class Operation(
    val destination: Destination,
    val navOptions: NavOptionsBuilder.() -> Unit,
)
