package com.example.secretpairproject.config.room

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*


class DateTimeConverter {

    companion object {

        val df = SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS")
    }

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}