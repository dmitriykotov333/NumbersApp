package com.kotdev.numbersapp.data.extensions

import com.kotdev.numbersapp.core_ui.enums.TypeRequest

fun String.toTypeRequest(): TypeRequest {
    return when (this.uppercase()) {
        TypeRequest.MATH.name -> TypeRequest.MATH
        TypeRequest.TRIVIA.name -> TypeRequest.TRIVIA
        TypeRequest.YEAR.name -> TypeRequest.YEAR
        TypeRequest.DATE.name -> TypeRequest.DATE
        else -> error("String not defined for TypeRequest: $this")
    }
}



