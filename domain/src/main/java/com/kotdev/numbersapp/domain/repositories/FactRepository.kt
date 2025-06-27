package com.kotdev.numbersapp.domain.repositories

import com.kotdev.numbersapp.domain.entities.FactRequest
import com.kotdev.numbersapp.domain.entities.StatusResponse
import kotlinx.coroutines.flow.MutableSharedFlow

interface FactRepository {

    val status: MutableSharedFlow<StatusResponse>

    suspend fun getFact(isRandom: Boolean, request: FactRequest)

}