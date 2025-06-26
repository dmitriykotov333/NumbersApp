package com.kotdev.numbersapp.database.fact

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    @Query("SELECT * FROM facts")
    fun facts(): Flow<List<FactDBO>>

    @Insert
    suspend fun insert(fact: FactDBO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserts(facts: List<FactDBO>)

    @Query("DELETE FROM facts")
    suspend fun clean()
}