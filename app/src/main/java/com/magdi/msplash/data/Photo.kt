package com.magdi.msplash.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.LocalDateTime


@Entity
data class Photo (
    @PrimaryKey val id: String,
    val color: String,
    val description: String,
    val url: Uri,
    val width: Int,
    val height: Int,
    val createdAt: LocalDateTime?
)