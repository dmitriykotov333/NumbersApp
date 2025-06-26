package com.kotdev.numbersapp.data

import android.util.Log
import com.kotdev.numbersapp.core.api.StatusRequest
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.data.ktor.KtorMainDataSource
import com.kotdev.numbersapp.data.mappers.mapToFactRequestRandom
import com.kotdev.numbersapp.data.network.networkBoundResource
import com.kotdev.numbersapp.database.FactDatabase
import com.kotdev.numbersapp.database.fact.FactDBO
import com.kotdev.numbersapp.database.fact.FactDao
import com.kotdev.numbersapp.domain.entities.FactData
import com.kotdev.numbersapp.domain.repositories.FactRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerialName
import androidx.room.withTransaction
import com.kotdev.numbersapp.core.api.prepare
import com.kotdev.numbersapp.data.mappers.mapToFactRequest
import com.kotdev.numbersapp.domain.entities.FactRequest
import javax.inject.Inject

class FactRandomRepository @Inject constructor(
    private val remoteDataSource: KtorMainDataSource,
    private val db: FactDao
) {

    fun firstMatch(): Flow<StatusRequest<List<FactDBO>>> = networkBoundResource(
        query = {
            db.facts()
        },
        fetch = {
            val response = coroutineScope {
                val deferred = TypeRequest.entries.mapIndexed { index, it ->
                    async {
                        when (it.mapToFactRequestRandom()) {
                            is FactRequest.Math -> remoteDataSource.math(true)
                            is FactRequest.Trivia -> remoteDataSource.trivia(true)
                            is FactRequest.Year -> remoteDataSource.year(true)
                            is FactRequest.Date -> remoteDataSource.date(true)
                        }
                    }
                }
                deferred.awaitAll()
            }
            response
        },
        saveFetchResult = { server ->
            val items = server.map {
                FactDBO(
                    type = it.prepare().data.type,
                    description = it.prepare().data.text
                )
            }
            db.clean()
            db.inserts(items)
        })

}