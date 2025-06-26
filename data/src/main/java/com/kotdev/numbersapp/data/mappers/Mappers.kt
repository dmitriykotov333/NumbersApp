package com.kotdev.numbersapp.data.mappers

import androidx.compose.runtime.Immutable
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.data.extensions.toTypeRequest
import com.kotdev.numbersapp.domain.entities.FactRequest
import com.kotdev.numbersapp.domain.entities.History


data class HistoryUI(
    val id: Long,
    val numbers: String,
    val type: TypeRequest,
    val description: String
)

fun History.mapToHistoryUi(): HistoryUI {
    return HistoryUI(
        id = this.id,
        numbers = this.numbers,
        type = this.type.toTypeRequest(),
        description = this.description
    )
}

fun TypeRequest.mapToFactRequest(vararg value: Int): FactRequest {
    return when (this) {
        TypeRequest.MATH -> FactRequest.Math(value = value[0])
        TypeRequest.TRIVIA -> FactRequest.Trivia(value = value[0])
        TypeRequest.YEAR -> FactRequest.Year(value = value[0])
        TypeRequest.DATE -> FactRequest.Date(
            month = value[0],
            day = value[1]
        )
    }
}

fun TypeRequest.mapToFactRequestRandom(): FactRequest {
    return when (this) {
        TypeRequest.MATH -> FactRequest.Math(value = -1)
        TypeRequest.TRIVIA -> FactRequest.Trivia(value = -1)
        TypeRequest.YEAR -> FactRequest.Year(value = -1)
        TypeRequest.DATE -> FactRequest.Date(month = -1, day = -1)
    }
}