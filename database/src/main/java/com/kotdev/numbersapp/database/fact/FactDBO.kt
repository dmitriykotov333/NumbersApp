package com.kotdev.numbersapp.database.fact

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class FactDBO(
    @ColumnInfo("type") val type: String,
    @ColumnInfo("description") val description: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)