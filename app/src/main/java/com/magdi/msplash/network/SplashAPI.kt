package com.magdi.msplash.network

import com.magdi.msplash.network.response.PhotosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SplashAPI {

    @GET("photos")
    suspend fun loadPhotos(
        @Query("per_page")
        perPage: Int,
        @Query("page")
        page: Int
    ): List<PhotosResponse>
}