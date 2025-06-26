package com.kotdev.numbersapp.database.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "history")
data class HistoryDBO(
    @ColumnInfo("type") val type: String,
    @ColumnInfo("numbers") val numbers: Int,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("createdAt") val createdAt: Date,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)