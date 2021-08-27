package com.magdi.msplash.data.mappers

import android.net.Uri
import com.magdi.msplash.data.Photo
import com.magdi.msplash.network.response.PhotosResponse
import com.magdi.msplash.utils.Mapper


object UnSplashPhotoMapper : Mapper<PhotosResponse, Photo> {
    private const val REGULAR_WIDTH = 1080

    override fun map(response: PhotosResponse): Photo {
        return Photo(
            id = response.id!!,
            url = Uri.parse(response.urls!!.regular!!),
            color = response.color.orEmpty(),
            description = response.description.orEmpty(),
            height = calcNewHeight(response.width, response.height),
            width = REGULAR_WIDTH,
            createdAt = LocaleDateTimeMapper.map(response.created_at)
        )
    }

    private fun calcNewHeight(width: Int, height: Int): Int {
        val scale: Double = height.toDouble() / width.toDouble()
        return (REGULAR_WIDTH.toDouble() * scale).toInt()
    }
}