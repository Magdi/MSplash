package com.magdi.msplash.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.magdi.msplash.usecase.LoadPhotoParams
import com.magdi.msplash.usecase.LoadPhotosUseCase
import com.magdi.msplash.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val loadPhotosUseCase: LoadPhotosUseCase
) : ViewModel() {

    private var isLoading : Boolean = false

    fun loadPhotos(currentPage: Int) = flow {
        Log.i(TAG, "load page $currentPage, is Loading $isLoading" )
        if (!isLoading) {
            loadPhotosUseCase(
                LoadPhotoParams(
                    page = currentPage,
                    perPage = PER_PAGE_COUNT
                )
            ).collect {
                isLoading = it is Results.Loading
                Log.i(TAG,"loading results $it (${it.data})" )
                emit(it)
            }
        }
    }


    companion object {
        private const val PER_PAGE_COUNT = 10
        private const val TAG = "PhotoListViewModel"
    }
}