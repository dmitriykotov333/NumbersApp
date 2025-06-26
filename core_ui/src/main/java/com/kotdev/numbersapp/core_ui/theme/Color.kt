package com.kotdev.numbersapp.core_ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.kotdev.numbersapp.core_ui.enums.TypeRequest

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

data class AppColors(
    val background: Color,
    val mathColor: Color,
    val triviaColor: Color,
    val yearColor: Color,
    val dateColor: Color,
    val link: Color,
) {
    val typeColors: Map<TypeRequest, Color> = mapOf(
        TypeRequest.MATH to mathColor,
        TypeRequest.TRIVIA to triviaColor,
        TypeRequest.YEAR to yearColor,
        TypeRequest.DATE to dateColor
    )
}

val palette = AppColors(
    background = Color(0xD90A396E),
    mathColor = Color(0xFF3F51B5),
    triviaColor = Color(0xFF00AF59),
    yearColor = Color(0xFF9A00B4),
    dateColor = Color(0xFFA6403B),
    link = Color(0xFF2196F3),
)

val paletteDark = AppColors(
    background = Color(0xD90A396E),
    mathColor = Color(0xFF3F51B5),
    triviaColor = Color(0xFF00AF59),
    yearColor = Color(0xFF0070A2),
    dateColor = Color(0xFFA6403B),
    link = Color(0xFF2196F3),
)

val LocalColorProvider = staticCompositionLocalOf<AppColors> { error("No default implementation") }