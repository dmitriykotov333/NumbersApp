package com.kotdev.numbersapp.core.utils

import android.content.Context
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import kotlinx.collections.immutable.persistentListOf

object Utils {
    val filter = persistentListOf(
        TypeRequest.MATH,
        TypeRequest.TRIVIA,
        TypeRequest.YEAR,
        TypeRequest.DATE
    )
    const val FILTER_DATA_STORE = "FILTER_DATA_STORE"
    const val SELECTED_TYPES = "selected_types"
    const val SORT_DESCENDING = "sort_descending"
}