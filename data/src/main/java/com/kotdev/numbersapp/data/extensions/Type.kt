package com.kotdev.numbersapp.data.extensions

import androidx.compose.ui.res.stringResource
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.enums.TypeRequest

fun String.toTypeRequest(): TypeRequest {
    return when (this.uppercase()) {
        TypeRequest.MATH.name -> TypeRequest.MATH
        TypeRequest.TRIVIA.name -> TypeRequest.TRIVIA
        TypeRequest.YEAR.name -> TypeRequest.YEAR
        TypeRequest.DATE.name -> TypeRequest.DATE
        else -> error("${stringResource(R.string.type_error)} $this")
    }
}



