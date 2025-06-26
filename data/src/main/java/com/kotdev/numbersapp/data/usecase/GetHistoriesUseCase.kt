package com.kotdev.numbersapp.data.usecase

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.data.HistoryRepository
import com.kotdev.numbersapp.data.extensions.toTypeRequest
import com.kotdev.numbersapp.data.mappers.HistoryUI
import com.kotdev.numbersapp.database.history.HistoryDBO
import com.kotdev.numbersapp.database.history.HistoryDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHistoriesUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    operator fun invoke(filter: Set<TypeRequest>): Flow<PagingData<HistoryUI>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                enablePlaceholders = false
            )
        ) {
            historyRepository.getPagingHistories(filter.map { it.name.lowercase() })
        }.flow.map { pagingData ->
            pagingData.map { entity ->
                HistoryUI(
                    id = entity.id,
                    numbers = entity.numbers.toString(),
                    type = entity.type.uppercase().toTypeRequest(),
                    description = entity.description
                )
            }
        }
    }
}