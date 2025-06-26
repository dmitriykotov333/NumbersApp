package com.kotdev.numbersapp.core_ui.enums

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.theme.Theme


interface UiType {
    val stringId: Int
}

enum class TypeRequest(
    @StringRes override val stringId: Int,
) : UiType {
    MATH(stringId = R.string.math),
    TRIVIA(R.string.trivia),
    YEAR(R.string.year),
    DATE(R.string.date);

}

@Composable
fun TypeRequest.color(): Color {
    return Theme.colors.typeColors[this]
        ?: error("Color not defined for TypeRequest: $this")
}