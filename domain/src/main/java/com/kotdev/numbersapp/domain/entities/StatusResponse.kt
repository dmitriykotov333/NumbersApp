package com.kotdev.numbersapp.domain.entities

import com.kotdev.numbersapp.core.api.StatusRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class StatusResponse(
    val response: StatusRequest<FactData> = StatusRequest.InProgress(),
    val isRandom: Boolean = false
)