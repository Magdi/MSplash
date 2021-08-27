package com.magdi.msplash.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Photo (
    @PrimaryKey val id: String,
    val color: String,
    val description: String,
    val url: String,
    val width: Int,
    val height: Int
)