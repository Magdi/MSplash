package com.magdi.msplash.data

import com.magdi.msplash.network.response.PhotosResponse
import com.magdi.msplash.utils.Mapper

class PhotoMapper : Mapper<PhotosResponse, Photo> {
    override fun map(response: PhotosResponse): Photo {
        return Photo(
            id = response.id!!,
            url = response.urls!!.regular!!,
            color = response.color.orEmpty(),
            description = response.description.orEmpty(),
            height = calcNewHeight(response.width, response.height),
            width = REGULAR_WIDTH
        )
    }

    private fun calcNewHeight(width: Int, height: Int) : Int {
        val scale : Double = height.toDouble() / width.toDouble()
        return (REGULAR_WIDTH.toDouble() * scale).toInt()
    }

    companion object {
        private const val REGULAR_WIDTH = 1080
    }
}