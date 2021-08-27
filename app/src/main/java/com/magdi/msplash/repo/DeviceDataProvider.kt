package com.magdi.msplash.repo

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.magdi.msplash.data.Photo
import dagger.hilt.android.qualifiers.ApplicationContext
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import java.lang.Exception
import javax.inject.Inject

class DeviceDataProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val projectionID = arrayOf(
        MediaStore.Images.Media._ID,  // index=0 for Cursor, column number=1 in SQL statement
        MediaStore.Images.Media.BUCKET_ID,  // index=1 for Cursor, column number=2 in SQL statement
        MediaStore.Images.Media.TITLE,  // index=2 for Cursor, column number=3 in SQL statement
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME
    )

    private val projectionImage = arrayOf(
        MediaStore.Images.Media._ID,                    // index=0 for Cursor, column number=1 in SQL statement
        MediaStore.Images.Media.BUCKET_ID,                // index=1 for Cursor, column number=2 in SQL statement
        MediaStore.Images.Media.TITLE,                    // index=2 for Cursor, column number=3 in SQL statement
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,    // index=3 for Cursor, column number=4 in SQL statement
        MediaStore.Images.Media.DATA,                    // index=4 for Cursor, column number=5 in SQL statement
        MediaStore.Images.Media.DESCRIPTION,            // index=5 for Cursor, column number=6 in SQL statement
        MediaStore.Images.Media.ORIENTATION,            // index=6 for Cursor, column number=7 in SQL statement
    )

    fun loadLocalPhotos(): ArrayList<Photo> {
        val localPhotos = ArrayList<Photo>()
        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projectionID,
            null,
            null,
            MediaStore.Images.Media.DEFAULT_SORT_ORDER
        )?.let { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
//            val widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.WIDTH)
//            val heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.HEIGHT)
//            val dateAddedC = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val photo = Photo(
                    id = id.toString(),
                    color = "#000000",
                    description = "",
                    url = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    ),
                    width = 1000,
                    height = 1000,
                    createdAt = null
                )
                localPhotos.add(photo)
            }
        }
        return localPhotos
    }
}