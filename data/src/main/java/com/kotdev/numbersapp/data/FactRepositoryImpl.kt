package com.kotdev.numbersapp.data

import androidx.room.ColumnInfo
import com.kotdev.numbersapp.core.api.ApiResponse
import com.kotdev.numbersapp.core.api.StatusRequest
import com.kotdev.numbersapp.core.api.wrapper
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.data.ktor.KtorMainDataSource
import com.kotdev.numbersapp.data.mappers.mapToFactRequest
import com.kotdev.numbersapp.database.FactDatabase
import com.kotdev.numbersapp.database.history.HistoryDBO
import com.kotdev.numbersapp.database.history.HistoryDao
import com.kotdev.numbersapp.domain.entities.FactData
import com.kotdev.numbersapp.domain.entities.FactRequest
import com.kotdev.numbersapp.domain.entities.StatusResponse
import com.kotdev.numbersapp.domain.repositories.FactRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val remoteDataSource: KtorMainDataSource,
    private val db: FactDatabase
) : FactRepository {

    override val status: MutableSharedFlow<StatusResponse> =
        MutableSharedFlow(extraBufferCapacity = 1)

    override suspend fun getFact(isRandom: Boolean, request: FactRequest) {
        wrapper(
            request = {
                when (request) {
                    is FactRequest.Math -> remoteDataSource.math(isRandom, request.value)
                    is FactRequest.Trivia -> remoteDataSource.trivia(isRandom, request.value)
                    is FactRequest.Year -> remoteDataSource.year(isRandom, request.value)
                    is FactRequest.Date -> remoteDataSource.date(
                        isRandom,
                        request.month,
                        request.day
                    )
                }
            }, cache = {
                db.historyDao.insert(
                    HistoryDBO(
                        it.type,
                        it.number,
                        it.text,
                        Date()
                    )
                )
            }, status = {
                status.tryEmit(
                    StatusResponse(
                        isRandom = isRandom,
                        response = it
                    )
                )
            }
        )
    }

}