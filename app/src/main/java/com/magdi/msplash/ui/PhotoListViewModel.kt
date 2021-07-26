package com.magdi.msplash.ui

import androidx.lifecycle.ViewModel
import com.magdi.msplash.usecase.LoadPhotoParams
import com.magdi.msplash.usecase.LoadPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val loadPhotosUseCase: LoadPhotosUseCase
) : ViewModel() {

    fun loadPhotos(currentPage: Int) = loadPhotosUseCase(
        LoadPhotoParams(
            page = currentPage,
            perPage = PER_PAGE_COUNT
        )
    )


    companion object {
        private const val PER_PAGE_COUNT = 50
    }
}