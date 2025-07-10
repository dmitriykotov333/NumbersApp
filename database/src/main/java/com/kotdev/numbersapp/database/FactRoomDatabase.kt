package com.kotdev.numbersapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kotdev.numbersapp.database.fact.FactDBO
import com.kotdev.numbersapp.database.fact.FactDao
import com.kotdev.numbersapp.database.history.HistoryDBO
import com.kotdev.numbersapp.database.history.HistoryDao


@Database(
    entities = [FactDBO::class, HistoryDBO::class],
    version = 3,
)
@TypeConverters(Converters::class)
internal abstract class FactRoomDatabase : RoomDatabase() {
    abstract fun factDao(): FactDao
    abstract fun historyDao(): HistoryDao
}

fun FactDatabase(applicationContext: Context): FactDatabase {
    val newsRoomDatabase =
        Room.databaseBuilder(
            checkNotNull(applicationContext.applicationContext),
            FactRoomDatabase::class.java,
            "numbersapp"
        ).fallbackToDestructiveMigration(false).build()
    return FactDatabase(newsRoomDatabase)
}