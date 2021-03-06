package com.magdi.msplash.usecase

import com.magdi.msplash.data.Photo
import com.magdi.msplash.di.IoDispatcher
import com.magdi.msplash.repo.PhotoRepo
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadPhotosUseCase @Inject constructor(
    private val photoRepo: PhotoRepo,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<Photo>>(dispatcher) {
    override fun execute(unit: Unit) = photoRepo.getPhotos()
}