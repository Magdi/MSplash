package com.magdi.msplash.data

data class Photo(
    val id: String,
    val color: String,
    val description: String,
    val url: String,
    val width: Int,
    val height: Int
) {
    fun getFileName(): String {
        return "$id$width$height"
    }
}