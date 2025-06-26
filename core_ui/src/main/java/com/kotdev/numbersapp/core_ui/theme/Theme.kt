package com.kotdev.numbersapp.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

object Theme {
    val colors: AppColors
        @Composable
        get() = LocalColorProvider.current

}

@Composable
fun NumbersAppTheme(
    content: @Composable () -> Unit
) {
    AppTheme(content)
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        if (isSystemInDarkTheme()) {
            LocalColorProvider provides paletteDark
        } else {
            LocalColorProvider provides palette
        },
        content = content
    )
}