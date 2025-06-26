package com.kotdev.numbersapp.core_ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.kotdev.numbersapp.core_ui.R

val FORMULAR = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.formular_medium, FontWeight.Medium
        ),
        Font(
            resId = R.font.formular_regular, FontWeight.Normal
        ),
    )
)

val GOTHIC = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.gothic_bold, FontWeight.Bold
        ),
        Font(
            resId = R.font.gothic_regular, FontWeight.Normal
        ),
    )
)

val GROTESK = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.grotesk_bold, FontWeight.Bold
        )
    )
)
