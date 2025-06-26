package com.kotdev.numbersapp.core.utils

import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import kotlinx.collections.immutable.persistentListOf

object Utils {
    val filter = persistentListOf(
        TypeRequest.MATH,
        TypeRequest.TRIVIA,
        TypeRequest.YEAR,
        TypeRequest.DATE
    )
}