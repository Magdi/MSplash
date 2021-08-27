package com.magdi.msplash.repo

import androidx.annotation.WorkerThread
import com.magdi.msplash.download.ImageDownloader
import com.magdi.msplash.data.Photo
import com.magdi.msplash.data.PhotoMapper
import com.magdi.msplash.dp.DownloadPhotoDB
import com.magdi.msplash.network.SplashAPI
import com.magdi.msplash.utils.Results
import kotlinx.coroutines.flow.flow
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class DownloadPhotosRepo @Inject constructor(
    private val api: SplashAPI,
    private val mapper: PhotoMapper,
    private val downloader: ImageDownloader,
    private val db: DownloadPhotoDB
) {
    @WorkerThread
    fun downloadPhotos() = flow {
        emit(Results.Loading())
        val response = try {
            val response = api.loadPhotos(PER_PAGE, 1)
            mapper.mapList(response)
        } catch (e: Exception) {
            emit(Results.Error(e.message))
            return@flow
        }
        emit(Results.Success(response))
    }

    fun downloadPhoto(photo: Photo) {
        downloader.downloadImage(photo)?.run {
            db.savePhotoFile(photo, this)
        }
    }

    fun getRandomPhoto(): File {
        return db.getRandom()
    }

    companion object {
        const val PER_PAGE = 10
    }
}