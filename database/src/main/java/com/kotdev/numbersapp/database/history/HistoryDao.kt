package com.kotdev.numbersapp.database.history

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY createdAt DESC")
    fun histories(): PagingSource<Int, HistoryDBO>

    @Query("SELECT * FROM history WHERE type IN (:types) ORDER BY createdAt DESC")
    fun getPagingSourceByTypes(types: List<String>): PagingSource<Int, HistoryDBO>

    @Insert
    suspend fun insert(history: HistoryDBO): Long

    @Query("SELECT * FROM history WHERE id = :id")
    suspend fun getById(id: Long): HistoryDBO

    @Query("DELETE FROM history")
    suspend fun clean()
}