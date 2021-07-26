package com.magdi.msplash.repo

import android.util.Log
import androidx.annotation.WorkerThread
import com.magdi.msplash.data.PhotoMapper
import com.magdi.msplash.network.SplashAPI
import com.magdi.msplash.usecase.LoadPhotoParams
import com.magdi.msplash.utils.Results
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class PhotoRepo @Inject constructor(
    private val api: SplashAPI,
    private val mapper: PhotoMapper
) {
    @WorkerThread
    fun getPhotos(params: LoadPhotoParams) = flow {
        Log.e("Repo", "loading  $params")
        emit(Results.Loading())
        val response = try {
            val response = api.loadPhotos(
                perPage = params.perPage,
                page = params.page
            )
            mapper.mapList(response)
        } catch (e: Exception) {
            emit(Results.Error(e.message))
            return@flow
        }
        emit(Results.Success(response))
    }
}