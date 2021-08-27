package com.magdi.msplash.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.magdi.msplash.usecase.LoadPhotoParams
import com.magdi.msplash.usecase.LoadPhotosUseCase
import com.magdi.msplash.usecase.RequestPhotosUseCase
import com.magdi.msplash.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val loadPhotosUseCase: LoadPhotosUseCase,
    private val requestPhotosUseCase: RequestPhotosUseCase,
) : ViewModel() {

    private var isLoading : Boolean = false

    fun loadPhotos() = loadPhotosUseCase(Unit)

    fun refreshPhotos(currentPage: Int) : Flow<Results<Boolean>> = flow {
        if (!isLoading) {
            requestPhotosUseCase(
                LoadPhotoParams(
                    page = currentPage,
                    perPage = PER_PAGE_COUNT
                )
            ).collect {
                when(it) {
                    is Results.Loading -> isLoading = true
                    else -> isLoading = false
                }
            }
        }
    }

    companion object {
        private const val PER_PAGE_COUNT = 10
        private const val TAG = "PhotoListViewModel"
    }
}