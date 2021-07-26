package com.magdi.msplash.network.response

data class PhotosResponse(
    val alt_description: String? = null,
    val blur_hash: String? = null,
    val color: String? = null,
    val created_at: String? = null,
    val description: String? = null,
    val height: Int,
    val id: String? = null,
    val updated_at: String? = null,
    val urls: Urls? = null,
    val width: Int
)