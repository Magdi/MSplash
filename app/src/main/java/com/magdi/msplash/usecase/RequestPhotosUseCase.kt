package com.magdi.msplash.usecase

import com.magdi.msplash.data.Photo
import com.magdi.msplash.di.IoDispatcher
import com.magdi.msplash.repo.PhotoRepo
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RequestPhotosUseCase @Inject constructor(
    private val photoRepo: PhotoRepo,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<LoadPhotoParams, Boolean>(dispatcher) {
    override fun execute(params: LoadPhotoParams) = photoRepo.requestPhotos(params)
}

data class LoadPhotoParams(val perPage: Int = 50, val page: Int = 1)