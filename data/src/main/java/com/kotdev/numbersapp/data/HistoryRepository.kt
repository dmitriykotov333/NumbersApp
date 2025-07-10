package com.kotdev.numbersapp.data

import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.kotdev.numbersapp.database.history.HistoryDBO
import com.kotdev.numbersapp.database.history.HistoryDao
import javax.inject.Inject


class HistoryRepository @Inject constructor(private val dao: HistoryDao) {
    fun getPagingHistories(types: List<String>, sortDescending: Boolean): PagingSource<Int, HistoryDBO> {
        val placeholders = types.joinToString(",") { "?" }
        val sql = "SELECT * FROM history WHERE type IN ($placeholders) ORDER BY createdAt ${if (sortDescending) "DESC" else "ASC"}"
        val query = SimpleSQLiteQuery(sql, types.toTypedArray())
        return dao.getPagingSourceByFilters(query)
    }
}