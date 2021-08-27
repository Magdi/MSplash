package com.magdi.msplash.download

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.magdi.msplash.data.Photo
import com.magdi.msplash.images.GlideApp
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ImageDownloader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun downloadImage(photo: Photo): File? {
        val file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.run {
            File("${this.path}${File.separator}${photo.getFileName()}")
        }
        GlideApp.with(context)
            .asBitmap()
            .load(photo.url)
            .into(DownloadTarget(file))
        return file
    }


    class DownloadTarget(private val file: File?) : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            if (file == null) {
                return
            }
            try {
                val outStream = FileOutputStream(file)
                resource.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
                outStream.close()
            } catch (_: IOException) {
                // do nothing
            }
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    }
}