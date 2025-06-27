package com.kotdev.numbersapp.data

import com.kotdev.numbersapp.core.api.wrapper
import com.kotdev.numbersapp.data.ktor.KtorMainDataSource
import com.kotdev.numbersapp.database.FactDatabase
import com.kotdev.numbersapp.database.history.HistoryDBO
import com.kotdev.numbersapp.domain.entities.FactRequest
import com.kotdev.numbersapp.domain.entities.StatusResponse
import com.kotdev.numbersapp.domain.repositories.FactRepository
import kotlinx.coroutines.flow.MutableSharedFlow
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