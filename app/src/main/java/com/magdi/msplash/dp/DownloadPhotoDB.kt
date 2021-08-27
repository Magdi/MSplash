package com.magdi.msplash.dp

import android.content.Context
import com.magdi.msplash.data.Photo
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class DownloadPhotoDB @Inject constructor(
    @ApplicationContext context: Context
) {

    private val photos = context.getSharedPreferences("photos", Context.MODE_PRIVATE)
    private val photosFiles = context.getSharedPreferences("photos_files", Context.MODE_PRIVATE)

    fun savePhotos(photoList: List<Photo>) {
        val db = photos.edit()
        photoList.forEach {
            db.putString(it.id, it.toString())
        }
        db.apply()
    }

    fun savePhotoFile(photo: Photo, file: File) {
        val db = photosFiles.edit()
        db.putString(photo.id, file.path)
        db.apply()
    }

    fun getRandom(): File {
        val path = photosFiles.all.values.random().toString()
        return File(path)
    }
}