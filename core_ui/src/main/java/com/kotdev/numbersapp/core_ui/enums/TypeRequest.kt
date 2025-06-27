package com.kotdev.numbersapp.core_ui.enums

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.theme.Theme


interface UiType {
    val stringId: Int
}

enum class TypeRequest(
    @StringRes override val stringId: Int,
) : UiType {
    MATH(stringId = R.string.math),
    TRIVIA(stringId = R.string.trivia),
    YEAR(stringId = R.string.year),
    DATE(stringId = R.string.date);

}

@Composable
fun TypeRequest.color(): Color {
    return Theme.colors.typeColors[this]
        ?: error("${stringResource(R.string.color_error)} $this")
}