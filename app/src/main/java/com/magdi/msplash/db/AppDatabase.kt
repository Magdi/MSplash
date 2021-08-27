package com.magdi.msplash.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.magdi.msplash.data.Photo

@Database(entities = [Photo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotoDao
}