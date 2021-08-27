package com.magdi.msplash.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.magdi.msplash.data.Photo

@Database(entities = [Photo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotoDao
}