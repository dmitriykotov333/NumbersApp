package com.kotdev.numbersapp.data.mappers

import com.kotdev.numbersapp.core.api.ApiResponse
import com.kotdev.numbersapp.core.api.prepare
import com.kotdev.numbersapp.core.extensions.formatCreatedAt
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.data.extensions.toTypeRequest
import com.kotdev.numbersapp.database.fact.FactDBO
import com.kotdev.numbersapp.database.history.HistoryDBO
import com.kotdev.numbersapp.domain.entities.FactData
import com.kotdev.numbersapp.domain.entities.FactRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class HistoryUI(
    val id: Long,
    val numbers: String,
    val type: TypeRequest,
    val description: String,
    val time: String
)

fun HistoryDBO.mapToHistoryUi(): HistoryUI {
    return HistoryUI(
        id = id,
        numbers = numbers.toString(),
        type = type.uppercase().toTypeRequest(),
        description = description,
        time = createdAt.time.formatCreatedAt()
    )
}

fun ApiResponse<FactData>.mapToFactDBO(): FactDBO {
    return FactDBO(
        type = prepare().data.type,
        description = prepare().data.text
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