package com.kotdev.numbersapp.database

import androidx.room.TypeConverter
import java.text.DateFormat
import java.util.Date


internal class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time 
}
