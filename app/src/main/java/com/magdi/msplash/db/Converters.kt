package com.magdi.msplash.db

import android.net.Uri
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

    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri.toString()
    }
}