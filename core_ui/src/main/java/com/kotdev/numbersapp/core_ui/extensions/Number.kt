package com.kotdev.numbersapp.core_ui.extensions

fun Int.splitToDigits(): List<Int> {
    return try {
        coerceAtMost(9999)
            .toString()
            .padStart(4, '0')
            .map {
                it.digitToInt()
            }
    } catch (e: Exception) {
        emptyList()
    }
}