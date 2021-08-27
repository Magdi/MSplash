package com.magdi.msplash.download

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.magdi.msplash.repo.DownloadPhotosRepo
import com.magdi.msplash.utils.Results
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect


@HiltWorker
class DownloadWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    val repo: DownloadPhotosRepo
) : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
        repo.downloadPhotos().collect {
            if (it is Results.Success) {
                it.data?.forEach { photo ->
                    repo.downloadPhoto(photo)
                }
            }
        }
        return Result.success()
    }
}