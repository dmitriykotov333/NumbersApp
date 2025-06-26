package com.kotdev.numbersapp.data

import androidx.paging.PagingSource
import com.kotdev.numbersapp.database.history.HistoryDBO
import com.kotdev.numbersapp.database.history.HistoryDao
import javax.inject.Inject


class HistoryRepository @Inject constructor(private val dao: HistoryDao) {
    fun getPagingHistories(types: List<String>): PagingSource<Int, HistoryDBO> {
        return dao.getPagingSourceByTypes(types)
    }
}