package com.kotdev.numbersapp.navigation.local

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.kotdev.numbersapp.navigation.core.AppNavigator

val LocalComposeNavigator: ProvidableCompositionLocal<AppNavigator> =
  staticCompositionLocalOf {
    error(
      "No Navigator provided!\nMake sure to wrap all usages of components in BibleTheme."
    )
  }


public val currentComposeNavigator: AppNavigator
  @Composable
  @ReadOnlyComposable
  get() = LocalComposeNavigator.current