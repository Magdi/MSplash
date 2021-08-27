package com.magdi.msplash.db

import androidx.room.TypeConverter
import org.joda.time.LocalDateTime

class Converters {
    @TypeConverter
    fun dateFromTimeStamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime(value) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: LocalDateTime?): Long? {
        return date?.toDateTime()?.millis
    }
}