package com.magdi.msplash.setWallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.magdi.msplash.repo.DownloadPhotosRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class SetWallpaperWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    val repo: DownloadPhotosRepo
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        val file = repo.getRandomPhoto()
        val wallpaperManager = WallpaperManager.getInstance(context)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.path)
            wallpaperManager.setBitmap(bitmap)
        }
        return Result.success()
    }
}