package com.kotdev.numbersapp.domain.entities

import com.kotdev.numbersapp.core_ui.enums.TypeRequest

sealed class FactRequest(val type: TypeRequest) {
    data class Math(val value: Int) : FactRequest(TypeRequest.MATH)
    data class Trivia(val value: Int) : FactRequest(TypeRequest.TRIVIA)
    data class Year(val value: Int) : FactRequest(TypeRequest.YEAR)
    data class Date(val month: Int, val day: Int) : FactRequest(TypeRequest.DATE)
}