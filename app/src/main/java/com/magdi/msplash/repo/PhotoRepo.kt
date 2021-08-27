package com.magdi.msplash.repo

import android.util.Log
import androidx.annotation.WorkerThread
import com.magdi.msplash.data.Photo
import com.magdi.msplash.data.PhotoMapper
import com.magdi.msplash.db.AppDatabase
import com.magdi.msplash.db.PhotoDao
import com.magdi.msplash.network.SplashAPI
import com.magdi.msplash.usecase.LoadPhotoParams
import com.magdi.msplash.utils.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class PhotoRepo @Inject constructor(
    private val dao: PhotoDao,
    private val api: SplashAPI,
    private val mapper: PhotoMapper
) {
    @WorkerThread
    fun getPhotos(): Flow<Results<List<Photo>>> {
        return dao.getAll().map { photoList ->
            Results.Success(data = photoList)
        }
    }


    @WorkerThread
    fun requestPhotos(params: LoadPhotoParams) = flow {
        Log.e("Repo", "loading  $params")
        emit(Results.Loading())
        try {
            val response = api.loadPhotos(
                perPage = params.perPage,
                page = params.page
            )
            dao.insertAll(mapper.mapList(response))
            emit(Results.Success(true))
        } catch (e: Exception) {
            emit(Results.Error(e.message))
            return@flow
        }

    }

    companion object {
        private const val TAG = "PhotoRepo"
    }
}