package com.kotdev.numbersapp.database.history

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY createdAt DESC")
    fun histories(): PagingSource<Int, HistoryDBO>

    @RawQuery(observedEntities = [HistoryDBO::class])
    fun getPagingSourceByFilters(query: SupportSQLiteQuery): PagingSource<Int, HistoryDBO>

    @Insert
    suspend fun insert(history: HistoryDBO): Long

    @Query("SELECT * FROM history WHERE id = :id")
    suspend fun getById(id: Long): HistoryDBO

    @Query("DELETE FROM history")
    suspend fun clean()
}