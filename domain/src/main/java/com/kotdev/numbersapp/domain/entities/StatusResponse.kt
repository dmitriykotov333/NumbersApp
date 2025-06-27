package com.kotdev.numbersapp.domain.entities

import com.kotdev.numbersapp.core.api.StatusRequest

data class StatusResponse(
    val response: StatusRequest<FactData> = StatusRequest.InProgress(),
    val isRandom: Boolean = false
)