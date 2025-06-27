package com.kotdev.numbersapp.data.extensions

import com.kotdev.numbersapp.core.api.ApiResponse
import com.kotdev.numbersapp.domain.entities.FactData
import com.kotdev.numbersapp.domain.entities.StatusResponse

fun ApiResponse<FactData>.toResult(): ApiResponse<FactData> {
    return when (this) {
        is ApiResponse.Success -> {
            if (!data.found) {
                ApiResponse.Error(message = data.text)
            } else {
              this
            }
        }
        else -> this
    }
}