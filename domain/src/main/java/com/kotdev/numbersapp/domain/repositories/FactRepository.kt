package com.kotdev.numbersapp.domain.repositories

import com.kotdev.numbersapp.core.api.ApiResponse
import com.kotdev.numbersapp.core.api.StatusRequest
import com.kotdev.numbersapp.domain.entities.FactData
import com.kotdev.numbersapp.domain.entities.FactRequest
import com.kotdev.numbersapp.domain.entities.StatusResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

interface FactRepository {
    val status: MutableSharedFlow<StatusResponse>

    suspend fun getFact(isRandom: Boolean, request: FactRequest)
}