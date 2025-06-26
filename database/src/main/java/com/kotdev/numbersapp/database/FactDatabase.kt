package com.kotdev.numbersapp.database

import com.kotdev.numbersapp.database.fact.FactDao
import com.kotdev.numbersapp.database.history.HistoryDao


class FactDatabase internal constructor(private val database: FactRoomDatabase) {
    val factDao: FactDao
        get() = database.factDao()
    val historyDao: HistoryDao
        get() = database.historyDao()
}